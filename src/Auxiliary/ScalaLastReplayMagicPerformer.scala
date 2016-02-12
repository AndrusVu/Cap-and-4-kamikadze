package Auxiliary

object ScalaLastReplayMagicPerformer {
    def makeReplayReadable(fileContent: String): String ={
      var splitedCommands = fileContent.split("\\r\\n")
      var readableForm = new String()

      var totalTime = 0
      for(command <- splitedCommands){
        var keys = command.split("\\t")
        totalTime += Integer.parseInt(keys(1))
        readableForm += commandCase(keys(0))

        def commandCase(key: String): String = key match{
          case "MOUSE_MOVED" => "Mouse was moved to (" + keys(2) + ", " + keys(3) + ") " +
            "position through " + keys(1) + " milliseconds after last action.\n"
          case "MOUSE_CLICKED" => "Mouse was clicked at (" + keys(2) + ", " + keys(3) + ") " +
            "position through " + keys(1) + " milliseconds after last action.\n"
          case "MOB_CREATED" => "Mob was created at (" + keys(2) + ", " + keys(3) + ") " +
            "position through " + keys(1) + " milliseconds after last action.\n"
          case "KEY_PRESSED" => "Key " + keys(2) + " was pressed " +
            "through " + keys(1) + " milliseconds after last action.\n"
          case "GAME_FINISHED" => "Game was finished " +
            "through " + keys(1) + " milliseconds after last action.\n" +
            "Total game time is: " + totalTime + " milliseconds or " +
            totalTime/1000 + " seconds or " +
            totalTime/60000 + " minutes"
        }
      }

      return readableForm
    }
}
