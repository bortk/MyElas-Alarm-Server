/*
 *  MyElas Zone Carbon Monoxide
 *
 *  Author: Elnar Hajiyev <ehajiyev@gmail.com>
 *  Date: 2015-11-17
 */

// for the UI
metadata {
  definition (name: "MyElas Zone Carbone Monoxide", author: "ehajiyev@gmail.com") {
    // Change or define capabilities here as needed
    capability "Refresh"
    capability "Carbon Monoxide Detector"
    capability "Sensor"
    capability "Polling"

    attribute "alarmState", "string"
    // Add commands as needed
    command "zone"
  }

  simulator {
    // Nothing here, you could put some testing stuff here if you like
  }

  tiles {
    // Main Row
    standardTile("zone", "device.alarmState", width: 2, height: 2, canChangeBackground: true, canChangeIcon: true) {
      state "clear",  label: 'clear',  icon: "st.alarm.carbon-monoxide.clear", backgroundColor: "#ffffff"
      state "detected",  label: 'GAS',  icon: "st.alarm.carbon-monoxide.detected", backgroundColor: "#e86d13"
      state "bypassed",  label: 'bypassed',  icon: "st.alarm.carbon-monoxide.detected", backgroundColor: "#ffffff"
    }

    // This tile will be the tile that is displayed on the Hub page.
    main "zone"

    // These tiles will be displayed when clicked on the device, in the order listed here.
    details(["zone"])
  }
}

// handle commands
def zone(String state) {
  def text = null
  def results = []
  // state will be a valid state for a zone (open, closed)
  // zone will be a number for the zone
  log.debug "Zone: ${state}"

  if (state == "detected") {
        text = "$device.displayName carbon monoxide was detected!"
        log.debug "$text"
        // these are displayed:false because the composite event is the one we want to see in the app
        sendEvent(name: "carbonMonoxide", value: "detected", descriptionText: text, displayed: false)

  } else if (state == "clear") {
        text = "$device.displayName carbon monoxide is clear"
        log.debug "$text"
        sendEvent(name: "carbonMonoxide", value: "clear", descriptionText: text, displayed: false)
  }

  // Send final event?
  sendEvent (name: "alarmState", value: "${newState}")
}

def poll() {
  log.debug "Executing 'poll'"
  // TODO: handle 'poll' command
  // On poll what should we do? nothing for now..
}

def refresh() {
  log.debug "Executing 'refresh' which is actually poll()"
  poll()
  // TODO: handle 'refresh' command
}