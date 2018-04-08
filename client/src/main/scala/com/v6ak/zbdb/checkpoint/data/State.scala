package com.v6ak.zbdb.checkpoint.data

abstract sealed class State(val resolved: Boolean)

object State{
  final case object Unknown extends State(false)
  final case object OnWay extends State(false)
  final case object Arrived extends State(false)
  final case object Departed extends State(true)
  final case object GivenUpThere extends State(true)
  final case object GivenUpBefore extends State(true)
}