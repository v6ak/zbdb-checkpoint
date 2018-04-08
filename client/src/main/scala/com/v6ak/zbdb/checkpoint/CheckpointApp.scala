package com.v6ak.zbdb.checkpoint

import com.v6ak.mdc
import com.v6ak.mdc.dialog.MDCDialog
import com.v6ak.mdc.drawer.MDCTemporaryDrawer
import com.v6ak.mdc.textField.MDCTextField
import com.v6ak.zbdb.checkpoint.Templates.{Descriptions, participantTableItem, statsItem}
import com.v6ak.zbdb.checkpoint.data.DataStub
import org.scalajs.dom
import org.scalajs.dom.ext._
import org.scalajs.dom.raw._

class CheckpointApp(pos: Int, name: String, year: Int) {

  private val drawer = new MDCTemporaryDrawer(dom.document.querySelector(".mdc-drawer--temporary"))

  val DynamicPages = Map(
    "departure-modal" -> new DeparturePage()
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

  def prepareDialog(id: String)(onOpen: MDCDialog => Unit): Unit = {
    val dialog = new MDCDialog(dom.document.getElementById(id))
    dom.document.getElementsByClassName(s"trigger-$id").foreach { element =>
      dom.console.log("trigger", element)
      element.addEventListener("click", (event: Event) => {
        onOpen(dialog)
        dialog.lastFocusedTarget = event.target
        dialog.show()
      })
    }
  }

  def prepareDialogs(): Unit = {
    //prepareDialog("departure-modal") { dialog => }
    prepareDialog("end-modal") { dialog => }
    prepareDialog("arrival-modal") { dialog =>
      val now = new scala.scalajs.js.Date()
      val arrivalTime = dom.document.getElementById("arrival-time").asInstanceOf[HTMLInputElement]
      arrivalTime.value = s"${now.getHours()}:${now.getMinutes()}"
      arrivalTime.parentNode.asInstanceOf[Element].querySelector("label").classList.add("mdc-floating-label--float-above")
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

  def initCheckpoint(): Unit = {
    mdc.autoInit()
    dom.window.addEventListener("hashchange", (_: Any) => {
      pageSwitcher.updateActivePage()
    })
    pageSwitcher.updateActivePage()
    prepareDialogs()
    loadParticipants()
    loadStats()
    enhanceElements(dom.document.asInstanceOf[Queryable])
  }

}
