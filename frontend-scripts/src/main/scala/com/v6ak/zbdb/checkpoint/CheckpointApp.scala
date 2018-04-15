package com.v6ak.zbdb.checkpoint

import com.v6ak.mdc
import com.v6ak.mdc.drawer.MDCTemporaryDrawer
import com.v6ak.mdc.textField.MDCTextField
import com.v6ak.util.DynDict
import com.v6ak.zbdb.checkpoint.Templates.{Descriptions, participantTableItem, statsItem}
import com.v6ak.zbdb.checkpoint.data.DataStub
import org.scalajs.dom
import org.scalajs.dom.ext._
import org.scalajs.dom.raw._

import scala.annotation.tailrec
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportTopLevel, JSGlobal}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import com.definitelyscala.googlepicker.google.picker.{Feature, PickerBuilder}

class CheckpointApp(pos: Int, name: String, year: Int) {

  private val drawer = new MDCTemporaryDrawer(dom.document.querySelector(".mdc-drawer--temporary"))

  val DynamicPages = Map(
    "departure-modal" -> (() => new DeparturePage()),
    "arrival-modal" -> (() => new ArrivalPage()),
    "end-modal" -> (() => new EndPage()),
  )

  val pageSwitcher = new PageSwitcher(
    defaultPage = "table",
    drawer = drawer,
    dynamicPages = DynamicPages,
    initialPage = "welcome",
    enhance = element => {enhanceElements(element.asInstanceOf[Queryable]); element}
  )

  def removeAllChildren(element: Element): Unit = {
    while (element.hasChildNodes()) {
      element.removeChild(element.lastChild)
    }
  }

  def loadStats(): Unit = {
    val statsElement = dom.document.getElementById("stats")
    removeAllChildren(statsElement)
    val stats = DataStub.participants.groupBy(_.state).mapValues(_.size).withDefaultValue(0)
    assert((stats.keySet -- Descriptions.keySet) == Set())
    for ((state, description) <- Descriptions) {
      statsElement.appendChild(
        statsItem(description, stats(state))
      )
    }
  }

  def loadParticipants(): Unit = {
    val participantsElement = dom.document.getElementById("participants")
    removeAllChildren(participantsElement)
    for (participant <- DataStub.participants) {
      participantsElement.appendChild(participantTableItem(participant, dirty = math.random() > 0.9).render)
    }
  }

  def enhanceElements(root: Queryable): Unit = {
    val mobileDetect = new MobileDetect(dom.window.navigator.userAgent)
    if (mobileDetect.is("Gecko") && mobileDetect.is("AndroidOS")) {
      // workaround for time fields
      root.querySelectorAll("""input[type="time"].mdc-text-field__input""").foreach {
        _.parentNode.asInstanceOf[Element].querySelector("label").classList.add("mdc-floating-label--float-above")
      }
    }
    def enhanceClass(className: String)(f: Node => Unit): Unit = {
      root.getElementsByClassName(className).foreach{node =>
        val enhancedClassName = s"$className--v6enhanced"
        val element = node.asInstanceOf[Element]
        if(!element.classList.contains(enhancedClassName)){
          f(element)
          element.classList.add(enhancedClassName)
        }
      }
    }
    enhanceClass("checkpoint-number")(_.textContent = pos.toString)
    enhanceClass("checkpoint-name")(_.textContent = name)
    //enhanceClass("participant-list-section")(_.appendChild(Templates.createParticipantsListPrototype().render))
    enhanceClass("participant-list-section")(_.appendChild(new ParticipantsSelectionComponent("id"+math.random()).content))
    enhanceClass("checkpoint-title")(_.textContent = s"#$pos $name")
    enhanceClass("menu")(_.addEventListener("click", (_: Any) => drawer.open = true))
    enhanceClass("mdc-text-field"){ field =>
      new MDCTextField(field)
    }
  }

  private def parseQueryString(qs: String) = qs.substring(1).split('&').map(s => s.indexOf('=') match {
    case -1 => (s, "")
    case index => (s.substring(0, index), s.substring(index+1))
  }).filter(_._1 != "").toMap

  private val google = new OAuth.Provider(DynDict(
    id = "google",   // required
    authorization_url = "https://accounts.google.com/o/oauth2/auth", // required
  ))

  private val AppId = "732287743219"

  private val SheetsClientId = "732287743219-l2u2j0ul1d4hif1gti0ep1rkofvs859u.apps.googleusercontent.com" // Dev ClientId; This probably cannot be reasonably kept in secret.

  def initCheckpoint(): Unit = {
    val hashParams = parseQueryString(dom.window.location.hash)
    val loggedIn = hashParams contains "state"
    if(loggedIn){
      val response = google.parse(dom.window.location.hash)
      dom.console.log(response)
      dom.window.location.hash = "#"
      def pickerCallback(data: PickerData): Unit = {
        dom.console.log("data", data)
        data.action match {
          case "picked" =>
            val doc = data.docs.head
            Ajax.get(
              url = s"https://sheets.googleapis.com/v4/spreadsheets/${doc.id}?includeGridData=false&ranges=Sheet1!A1:B5", // &key=$SheetsDevKey
              headers = Map("Authorization" -> s"Bearer ${response.access_token}")
            ).onComplete{
              case Failure(e) =>
                //dom.console.log(e.asInstanceOf[js.Any])
                e.printStackTrace()
                dom.window.alert("Failed to load data")
              case Success(data) =>
                dom.window.alert(data.responseText)
                dom.console.log(data)
            }
          case "loaded" =>
          case other => dom.window.alert("Něco se pokakazilo…")
        }
      }
      GapiLoad{
        dom.console.log("gapiload")
        gapi.load("auth2", DynDict(callback = () => {
          dom.console.log("auth2 load")
          gapi.load("picker", DynDict( callback = () =>{
            dom.console.log("picker load")
            val builder = new PickerBuilder().
              addView(dom.window.asInstanceOf[js.Dynamic].google.picker.ViewId.SPREADSHEETS).
              setOAuthToken(response.access_token).
              setAppId(AppId).
              //setDeveloperKey(PickerDevKey).
              setOrigin(dom.window.location.protocol + "//" + dom.window.location.host).
              setCallback(pickerCallback _).
              enableFeature(Feature.NAV_HIDDEN)
            dom.console.log(builder.toUri().toString())
            val picker = builder.build()
            picker.setVisible(true)
          }))
        }))
      }
    }
    // TODO: fix pageSwitcher intolerance of hashParams
    mdc.autoInit()
    dom.window.addEventListener("hashchange", (_: Any) => {
      pageSwitcher.updateActivePage()
    })
    pageSwitcher.updateActivePage()
    loadParticipants()
    loadStats()
    enhanceElements(dom.document.asInstanceOf[Queryable])

    if(!loggedIn) {
      // Create a new request
      var request = new OAuth.Request(DynDict(
        client_id = SheetsClientId, // required
        scope = "https://www.googleapis.com/auth/drive.file",
        redirect_uri = dom.window.location.protocol + "//" + dom.window.location.host + dom.window.location.pathname,
      ))

      // Give it to the provider
      var uri = google.requestToken(request)

      // Later we need to check if the response was expected
      // so save the request
      google.remember(request)

      // Do the redirect
      dom.window.location.href = uri
    }


  }

}

@js.native
@JSGlobal("""oauth2-client-js""")
object OAuth extends js.Object {

  @js.native
  class Provider(params: js.Dictionary[js.Any]) extends js.Object {
    def parse(hash: String) : Response = js.native

    def remember(request: Request): Unit = js.native

    def requestToken(request: Request): String = js.native

  }

  @js.native
  class Response(val response: js.Dictionary[String], val access_token: String, val token_type: String, refresh_token: String, expires_in: Int, scope: js.Any, state: String, metadata: js.Dictionary[String]) extends js.Object

  @js.native
  class Request(params: js.Dictionary[js.Any]) extends js.Object

}

@js.native
@JSGlobal("gapi")
object gapi extends js.Object{
  def load(str: String, options: js.Dictionary[js.Any]): Unit = js.native


  @js.native
  object client extends js.Object {
    def init(options: js.Dictionary[js.Any]) = js.native
  }

}

object GapiLoad {

  private var loaded = false

  private val queue = new js.Array[() => Unit]()

  def apply(f: =>Unit): Unit ={
    if(loaded){
      dom.console.log("GapiLoad", "already loaded")
      dom.window.setTimeout(() => f, 0)
    }else{
      dom.console.log("GapiLoad", "queued")
      queue.push(() => f)
    }
  }

  @tailrec
  private def consumeQueue(): Unit = {
    if(queue.length > 0){
      val function = queue.shift()
      dom.console.log("consumeQueue", function)
      dom.window.setTimeout(function, 0)
      consumeQueue()
    }
  }

  @JSExportTopLevel("onGapiLoad")
  def onGapiLoad(): Unit ={
    dom.console.log("GapiLoad", "onGapiLoad")
    loaded = true
    consumeQueue()
  }

}


@js.native
trait PickerData extends js.Object {
  def action: String
  def docs: js.Array[PickedDocument]
  def viewToken: js.Array[js.Any]
}

@js.native
trait PickedDocument extends js.Object{
  def id: String
  def serviceId: String
  def mimeType: String
  def name: String
  def description: String
  def `type`: String
  def lastEditedUtc: Long
  def iconUrl: String
  def url: String
  def embedUrl: String
  def isShared: Boolean
  def sizeBytes: Long
}