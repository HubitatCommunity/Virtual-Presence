/**
 *  Virtual GasDetector
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
 *    2018-07-18  C Steele       Revamped into Presence
 *    2018-07-18  Dan Ogorchock  Original Creation as "Virtual Illuminance Sensor"
 *
 * 
 */
metadata {
	definition (name: "Virtual GasDetector Hybrid", namespace: "csteele", author: "C Steele") {
		capability "GasDetector"
		capability "Sensor"
		capability "Switch"

		command "clear"
		command "detected"
		command "tested"
 
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


def clear() {
	sendEvent(name: "naturalGas", value:"clear", descriptionText: "naturalGas", unit: "")
	sendEvent(name: "switch", value:"on", descriptionText: "naturalGas", unit: "")
	if (logEnable) log.debug("Clear")
}

def detected() {
	sendEvent(name: "naturalGas", value:"detected", descriptionText: "naturalGas", unit: "")
	sendEvent(name: "switch", value:"off", descriptionText: "naturalGas", unit: "")
	if (logEnable) log.debug("Detected")
}

def tested() {
	sendEvent(name: "naturalGas", value:"tested", descriptionText: "naturalGas", unit: "")
	if (logEnable) log.debug("Tested")
}

def on() {
	sendEvent(name: "naturalGas", value:"clear", descriptionText: "naturalGas", unit: "")
	sendEvent(name: "switch", value:"on", descriptionText: "naturalGas", unit: "")
	if (logEnable) log.debug("on")
}

def off() {
	sendEvent(name: "naturalGas", value:"detected", descriptionText: "naturalGas", unit: "")
	sendEvent(name: "switch", value:"off", descriptionText: "naturalGas", unit: "")
	if (logEnable) log.debug("off")
}

def installed(){}

def configure() {}

def updated() {
    log.trace "Updated()"
    log.warn "debug logging is: ${logEnable == true}"
    log.warn "description logging is: ${txtEnable == true}"
    if (logEnable) runIn(1800,logsOff)    
}
