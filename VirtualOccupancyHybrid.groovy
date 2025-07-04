/**
 *  Virtual Occupancy Sensor
 * 
 */
metadata {
	definition (name: "Virtual Occupancy Hybrid", namespace: "csteele", author: "C Steele") {
		capability "PresenceSensor"
		capability "MotionSensor"
		capability "Sensor"
		capability "Switch"
 
 		command	'arrived'
 		command	'departed'
 		command	'occupied'
 		command	'unoccupied'
 		command	'active'
 		command	'inactive'

		attribute	'occupancy', 'STRING'

	}

      preferences {
          //standard logging options
          input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
      }
}

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

def parse(String description) {
	log.debug(${description})
	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def active() {
	sendEvent(name: "presence", value:"present", descriptionText: "presence", unit: "")
	sendEvent(name: "occupancy", value:"occupied", descriptionText: "occupied", unit: "")
	sendEvent(name: "motion", value:"active", descriptionText: "active", unit: "")
	sendEvent(name: "switch", value:"on", descriptionText: "presence", unit: "")
	if (logEnable) log.debug("Active")
}

def inactive() {
	sendEvent(name: "presence", value:"not present", descriptionText: "presence", unit: "")
	sendEvent(name: "occupancy", value:"unoccupied", descriptionText: "unoccupied", unit: "")
	sendEvent(name: "motion", value:"inactive", descriptionText: "inactive", unit: "")
	sendEvent(name: "switch", value:"off", descriptionText: "presence", unit: "")
	if (logEnable) log.debug("Inactive")
}

def occupied() {
	sendEvent(name: "presence", value:"present", descriptionText: "presence", unit: "")
	sendEvent(name: "occupancy", value:"occupied", descriptionText: "occupied", unit: "")
	sendEvent(name: "motion", value:"active", descriptionText: "active", unit: "")
	sendEvent(name: "switch", value:"on", descriptionText: "presence", unit: "")
	if (logEnable) log.debug("Occupied")
}

def unoccupied() {
	sendEvent(name: "presence", value:"not present", descriptionText: "presence", unit: "")
	sendEvent(name: "occupancy", value:"unoccupied", descriptionText: "unoccupied", unit: "")
	sendEvent(name: "motion", value:"inactive", descriptionText: "inactive", unit: "")
	sendEvent(name: "switch", value:"off", descriptionText: "presence", unit: "")
	if (logEnable) log.debug("Unoccupied")
}

def arrived() {
	sendEvent(name: "presence", value:"present", descriptionText: "presence", unit: "")
	sendEvent(name: "occupancy", value:"occupied", descriptionText: "occupied", unit: "")
	sendEvent(name: "motion", value:"active", descriptionText: "active", unit: "")
	sendEvent(name: "switch", value:"on", descriptionText: "presence", unit: "")
	if (logEnable) log.debug("Arrived")
}

def departed() {
	sendEvent(name: "presence", value:"not present", descriptionText: "presence", unit: "")
	sendEvent(name: "occupancy", value:"unoccupied", descriptionText: "unoccupied", unit: "")
	sendEvent(name: "motion", value:"inactive", descriptionText: "inactive", unit: "")
	sendEvent(name: "switch", value:"off", descriptionText: "presence", unit: "")
	if (logEnable) log.debug("Departed")
}

def on() {
	sendEvent(name: "presence", value:"present", descriptionText: "presence", unit: "")
	sendEvent(name: "occupancy", value:"occupied", descriptionText: "occupied", unit: "")
	sendEvent(name: "motion", value:"active", descriptionText: "active", unit: "")
	sendEvent(name: "motion", value:"inactive", descriptionText: "inactive", unit: "")
	sendEvent(name: "switch", value:"on", descriptionText: "presence", unit: "")
	if (logEnable) log.debug("On")
}

def off() {
	sendEvent(name: "presence", value:"not present", descriptionText: "presence", unit: "")
	sendEvent(name: "occupancy", value:"unoccupied", descriptionText: "unoccupied", unit: "")
	sendEvent(name: "switch", value:"off", descriptionText: "presence", unit: "")
	if (logEnable) log.debug("Off")
}

def installed(){}

def configure() {}

def updated() {
    log.trace "Updated()"
    log.warn "debug logging is: ${logEnable == true}"
    log.warn "description logging is: ${txtEnable == true}"
    if (logEnable) runIn(1800,logsOff)    
}
