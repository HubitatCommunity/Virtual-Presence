/**
 *  Hybrid Contact Sensor
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
 *    2022-08-15  C Steele       Refactored and added Invert & autoOff
 *    2021-06-16  C Steele       Revamped into Contact
 *    2018-07-18  C Steele       Revamped into Presence
 *    2018-07-18  Dan Ogorchock  Original Creation as "Virtual Illuminance Sensor"
 *
 * 
 */
metadata {
	definition (name: "Virtual Contact Hybrid", namespace: "csteele", author: "C Steele") {
		capability "Contact Sensor"
		capability "Sensor"
		capability "Switch"
 
 		command    "open"
 		command    "closed"
       
	}

      preferences {
		input name: "invert", type: "bool", title: "<b>Logical Invert</b>", description: "<i>On becomes Off, Off becomes On</i>", defaultValue: false
		input name: "autoOff", type: "enum", title: "<b>Enable Auto-Off (Auto-On)</b>", description: "<i>Automatically turns off (on) the device after selected time.</i>", options: [[0:"Disabled"],[1:"1s"],[2:"2s"],[5:"5s"],[10:"10s"],[20:"20s"],[30:"30s"],[60:"1m"],[120:"2m"],[300:"5m"],[1800:"30m"],[3200:"60m"]], defaultValue: 0
		//standard logging options
		input name: "debugOutput", type: "bool", title: "<b>Enable debug logging?</b>", defaultValue: true
		input name: "descTextEnable", type: "bool", title: "<b>Enable descriptionText logging?</b>", defaultValue: true
      }
}


void open()		{ invert ? doOff() : doOn() }
void closed()	{ invert ? doOn() : doOff() }
void on() 		{ invert ? doOff() : doOn() }
void off() 		{ invert ? doOn() : doOff() }

void doOn() {
	sendEvent(name: "contact", value:"open", descriptionText: "contact", unit: "")
	sendEvent(name: "switch", value:"on", descriptionText: "switch", unit: "")
	if (autoOff.toInteger()>0){ runIn(autoOff.toInteger(), off) }
	if (descTextEnable) log.info("On/Open")
}

void doOff() {
	sendEvent(name: "contact", value:"closed", descriptionText: "contact", unit: "")
	sendEvent(name: "switch", value:"off", descriptionText: "switch", unit: "")
	if (descTextEnable) log.info("Off/Closed")
}


void parse(String description) {
	log.debug(${description})
	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}

void installed() {}

void configure() {}

void updated() {
   if (descTextEnable) log.info "Updated()"
    log.warn "debug logging is: ${debugOutput == true}"
    log.warn "description logging is: ${descTextEnable == true}"
    log.warn "invert is: ${invert == true}"
    if (debugOutput) runIn(1800,logsOff)    
}

void logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("debugOutput",[value:"false",type:"bool"])
}

