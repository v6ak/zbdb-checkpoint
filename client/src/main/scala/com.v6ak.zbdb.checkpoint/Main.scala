package com.v6ak.zbdb.checkpoint



object Main{

  def main(args: Array[String]): Unit = {
    new CheckpointApp(1, "Pokusné stanoviště", 1984).initCheckpoint()
  }

}
