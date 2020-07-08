/*
 * Import URL: https://raw.githubusercontent.com/HubitatCommunity/??/master/??-Driver.groovy"
 *
 *	Copyright 2019 C Steele
 *
 *	Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *	use this file except in compliance with the License. You may obtain a copy
 *	of the License at:
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *	License for the specific language governing permissions and limitations
 *	under the License.
 *
 *
 */
	public static String version()      {  return "v0.1"  }
 
 
import groovy.transform.Field

metadata 
{
	definition(name: "Virtual PowerMeter", namespace: "csteele", author: "C Steele", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/??/master/??.groovy")
	{
 		capability "Switch"
 		capability "Configuration"
 		capability "SwitchLevel"
 		capability "Refresh"
 		capability "EnergyMeter"
 		capability "PowerMeter"
 		capability "Sensor"
 		capability "Actuator"
 		capability "VoltageMeasurement"
 		capability "Outlet"
 		
		attribute "current", "decimal"
		attribute "currentH", "decimal"
		attribute "currentL", "decimal"
		attribute "energyDuration", "number"
		attribute "powerH", "decimal"
		attribute "powerL", "decimal"
		attribute "voltageH", "decimal"
		attribute "voltageL", "decimal"

		command "setPower", ["NUMBER"]
		command "setVoltage", ["NUMBER"]
		command "resetCurrent"
		command "resetEnergy"
		command "resetPower"
		command "resetVoltage"
	}

      preferences 
      {
      	input name: "lineV", type: "enum", options: [110: "120 vac", 240: "240 vac"], title: "Nominal Line Voltage", defaultValue: true
        //standard logging options
		input name: "logEnable", type: "bool", title: "Enable debug logging", defaultValue: true
      }
}


/*
	updated
    
	Doesn't do much other than call initialize().
*/
def updated()
{
	initialize()
	unschedule()
      if (debugOutput) runIn(1800,logsOff) //disable debug logs after 30 min
	log.trace "updated ran - $lineV"
}


/*

	generic driver stuff

*/

def logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("debugOutput",[value:"false",type:"bool"])
}



/*
	installed
    
	Doesn't do much other than call initialize().
*/
def installed()
{
	initialize()
	log.trace "installed ran"
	state.voltage = 110d
}



/*
	initialize
    
	Doesn't do anything.
*/
def initialize()
{
	log.trace "initialize ran"
}


/*
	parse
    
	In a virtual world this should never be called.
*/
def parse(String description)
{
	log.trace "Description is $description"
}

/*
	on
    
	Turns the device on.
*/
def on()
{
	def descriptionText = "${device.displayName} is ${action}"
	log.trace "$device ON"	
	sendEvent(name: "switch", value: "on", descriptionText: descriptionText, type:type, unit:"")
}


/*
	off
    
	Turns the device off.
*/
def off()
{
	log.trace "$device OFF"
	def descriptionText = "${device.displayName} is ${action}"
	sendEvent(name: "switch", value: "off", descriptionText: descriptionText, type:type, unit:"")
	sendEvent(name: "power", value: 0, descriptionText: descriptionText, type:type, unit:"W")
	sendEvent(name: "current", value: 0, descriptionText: descriptionText, type:type, unit:"A")
}

def setPower(val) {
	log.trace "$device setPowerLevel $val"
	if (state.voltage > 0) def amps = val / state.voltage
	def descriptionText = "${device.displayName} setPower [$val]"
	sendEvent(name: "power", value: val, descriptionText: descriptionText, type:type, unit:"W")
	sendEvent(name: "current", value: amps, descriptionText: descriptionText, type:type, unit:"A")
}
def setLevel(val) {
	log.trace "${device.displayName} setLevel $val"
}
def setVoltage(val) {
	log.trace "${device.displayName} setVoltage $val"
	state.voltage = val
	def descriptionText = "${device.displayName} setVoltage"
	sendEvent(name: "voltage", value: val, descriptionText: descriptionText, type:type, unit:"V")
}


def resetCurrent() {
	def descriptionText = "${device.displayName} is ${action}"
	log.trace "${device.displayName} resetCurrent"
	sendEvent(name: "current", value: 0, descriptionText: descriptionText, type:type, unit:"A")
}
def resetEnergy() {
	def descriptionText = "${device.displayName} is ${action}"
	log.trace "${device.displayName} resetEnergy"
	sendEvent(name: "energy", value: 0, descriptionText: descriptionText, type:type, unit:"w")
}
def resetPower() {
	log.trace "${device.displayName} resetPower"
	def descriptionText = "${device.displayName} resetPower"
	sendEvent(name: "power", value: 0, descriptionText: descriptionText, type:type, unit:"W")
}
def resetVoltage() {
	log.trace "${device.displayName} resetVoltage"
	def descriptionText = "${device.displayName} resetVoltage"
	sendEvent(name: "voltage", value: 0, descriptionText: descriptionText, type:type, unit:"V")
}
def configure() {
	log.trace "${device.displayName} configured"
	installed()
}


// Check Version   ***** with great thanks and acknowlegment to Cobra (CobraVmax) for his original code ****
//--> Add per VersionTemplate_Apps.groovy

private setConfigured(configure) {
    updateDataValue("configured", configure)
}

private isConfigured() {
    getDataValue("configured") == "true"
}
