package Auxiliary

import java.io._
import java.util
;
import scala.collection.mutable
import scala.collection.mutable.MutableList;
import collection.JavaConversions._

class ScalaStatisticCollector(replayFiles: Array[File]) {
  def collect(): java.util.List[java.util.Map[String, Integer]] = {
    var summaryStatMap: mutable.Map[String, Integer] = new mutable.HashMap()
    var statMapsList: java.util.List[java.util.Map[String, Integer]] = new util.LinkedList[java.util.Map[String, Integer]]()

    for (file <- replayFiles) {
      var fileContent: String = ReplayFilesLoader.readReplayFile(file.getName())
      var commands: Array[String] = fileContent.split("\\r\\n")

      var replayStatMap: mutable.Map[String, Integer] = new mutable.HashMap()
      for (command <- commands) {
        var commandName: String = command.split("\\t")(0)
        replayStatMap.put(commandName, replayStatMap.getOrDefault(commandName, 0) + 1)
      }

      replayStatMap.foreach { keyValPair => summaryStatMap.put(
        keyValPair._1, summaryStatMap.getOrDefault(keyValPair._1, 0) + keyValPair._2) }
      statMapsList.add(replayStatMap)
    }
    statMapsList.add(summaryStatMap)
    return statMapsList;
  }
}