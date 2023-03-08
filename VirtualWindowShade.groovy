/**
 *  Virtual Window Shade
 *
 *  Copyright 2017 Daniel Ogorchock
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Change History:
 *
 *    Date        Who            What
 *    ----        ---            ----
 *    2018-07-18  C Steele       Revamped into Window Shade
 *    2018-07-18  Dan Ogorchock  Original Creation as "Virtual Illuminance Sensor"
 *
 * 
 */
metadata {
	definition (name: "Virtual Window Shade", namespace: "csteele", author: "C Steele") {
		capability "Window Shade"
		capability "Sensor"
		capability "Switch"
		capability "Actuator"
		capability	"Switch Level"
	}

      preferences {
          input name: "transition", type: "enum", title: "Transition time, (default:10 Seconds)", options: [0:"instant", 1:"1 second", 2:"2 seconds", 5:"5 seconds", 10:"10 seconds", 20:"20 seconds", 50:"50 seconds", 60:"1 minute", 120:"2 minutes"], required: true, defaultValue: "10"
          //standard logging options
          input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
      }
}

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("logEnable",[value:"false",type:"bool"])
}

// not used, but it's 'free', so I'm leaving it.
def parse(String description) {
	log.warn(${description})
	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def nextState(toState) {
	log.debug "nextState: ${toState}"
	switch(toState) {
		case "open":
	   		sendEvent(name: "switch", value: "on")
	   		sendEvent(name: "position", value: "100")
	   		sendEvent(name: "level", value: "100")
			break

		case "closed":
	   		sendEvent(name: "switch", value: "off")
	   		sendEvent(name: "position", value: "0")
	   		sendEvent(name: "level", value: "0")
			break

		case "partially open":
			sendEvent(name: "position", value: state.pos.toInteger())
			sendEvent(name: "switch", value:"on")
			sendEvent(name: "level", value: state.pos.toInteger())
			break
	}

	sendEvent(name: "windowShade", value: "${toState}")
}

def on()  { open()  }

def off() { close() }

def open() {
	sendEvent(name: "windowShade", value:"opening")
	runIn(transition.toInteger(), nextState, [data:"open"])
	if (logEnable) log.debug("Open")
}

def close() {
	sendEvent(name: "windowShade", value:"closing")
	runIn(transition.toInteger(), nextState, [data:"closed"])
	if (logEnable) log.debug("Closed")
}

def setPosition(pos) {
	state.pos = pos
      if (pos > 98) {
         open()
         return
      }
      if (pos <2) {
         close()
         return
      }
	sendEvent(name: "windowShade", value:"unknown")
      runIn(transition.toInteger(), nextState, [data:"partially open"])
	if (logEnable) log.debug("Partially Open")
}

// not used, but it's 'free'. If people use a Dimmer for their shades, then it sends setLevel.
def setLevel(pos) { setPosition(pos) }


def installed(){}

def configure() {}

def updated() {
    log.trace "Updated()"
    log.warn "debug logging is: ${logEnable == true}"
    log.warn "description logging is: ${txtEnable == true}"
    log.warn "transition time is: $transition"
    if (logEnable) runIn(1800,logsOff)    
}
