/*
 * Import URL: https://raw.githubusercontent.com/HubitatCommunity/Virtual-Presence/master/VirtualPowerPlug.groovy"
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
 
 
metadata 
{
	definition(name: "Virtual Power Plug", namespace: "csteele", author: "C Steele", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/Virtual-Presence/master/VirtualPowerPlug.groovy")
	{
 		capability "Switch"
 		capability "Actuator"
		capability "AccelerationSensor"
 		capability "Configuration"
 		capability "EnergyMeter"
 		capability "Outlet"
 		capability "PowerMeter"
 		capability "Refresh"
 		capability "Sensor"
 		capability "SwitchLevel"
 		capability "VoltageMeasurement"
 		
 		attribute  "current",        "number"
 		attribute  "currentH",       "number"
 		attribute  "currentL",       "number"
 		attribute  "energyDuration", "number"
 		attribute  "powerH",         "number"
 		attribute  "powerL",         "number"
 		attribute  "voltageH",       "number"
 		attribute  "voltageL",       "number"
 		
 		command    "resetCurrent"
 		command    "resetEnergy"
 		command    "resetPower"
 		command    "resetVoltage"

 		command    "setCurrent", ["number"]
 		command    "setEnergy",  ["number"]
 		command    "setPower",   ["number"]
 		command    "setVoltage", ["number"]

 		command    "active"
 		command    "inactive"
	}

      preferences 
      {

          //standard logging options
		input name: "debugOutput",   type: "bool", title: "<b>Enable debug logging?</b>",   description: "<br>", defaultValue: true
//		input name: "descTextEnable", type: "bool", title: "<b>Enable descriptionText logging?</b>", defaultValue: true
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
     // if (debugOutput) runIn(1800,logsOff) //disable debug logs after 30 min
	if (debugOutput) log.debug "updated ran"
}


def resetCurrent() {
	if (debugOutput) log.debug "$device resetCurrent"
	sendEvent(name: "current",   value:0)	
	sendEvent(name: "currentH",  value:0)	
	sendEvent(name: "currentL",  value:0)	
}
def resetEnergy() {
	if (debugOutput) log.debug "$device resetEnergy"
	sendEvent(name: "energy",          value:0)	
	sendEvent(name: "energyDuration",  value:0)	
}
def resetPower() {
	if (debugOutput) log.debug "$device resetPower"
	sendEvent(name: "power",   value:0)	
	sendEvent(name: "powerH",  value:0)	
	sendEvent(name: "powerL",  value:0)	
}
def resetVoltage() {
	if (debugOutput) log.debug "$device resetVoltage"
	sendEvent(name: "voltage",   value:0)	
	sendEvent(name: "voltageH",  value:0)	
	sendEvent(name: "voltageH",  value:0)	
}

def setCurrent(val) {
	if (debugOutput) log.debug "$device setCurrent: $val"
	sendEvent(name: "current",   value:val)	
	sendEvent(name: "currentH",  value:val)	
	sendEvent(name: "currentL",  value:val)	
}
def setEnergy(val) {
	if (debugOutput) log.debug "$device setEnergy: $val"
	sendEvent(name: "energy",          value:val)	
	sendEvent(name: "energyDuration",  value:val)	
}
def setPower(val) {
	if (debugOutput) log.debug "$device setPower: $val"
	sendEvent(name: "power",   value:val)	
	sendEvent(name: "powerH",  value:val)	
	sendEvent(name: "powerL",  value:val)	
}
def setVoltage(val) {
	if (debugOutput) log.debug "$device setVoltage: $val"
	sendEvent(name: "voltage",   value:val)	
	sendEvent(name: "voltageH",  value:val)	
	sendEvent(name: "voltageH",  value:val)	
}
def active()
{
	// The server will update accelerator status
	if (debugOutput) log.debug "$device Active"
	sendEvent(name: "acceleration",  value:"active")	
}
def inactive()
{
	// The server will update accelerator status
	if (debugOutput) log.debug "$device Inactive"
	sendEvent(name: "acceleration",  value:"inactive")	
}


/*

	generic driver stuff

*/


/*
	installed
    
	Doesn't do much other than call initialize().
*/
def installed()
{
	initialize()
	if (debugOutput) log.debug "installed ran"
}



/*
	initialize
    
	Doesn't do anything.
*/
def initialize()
{
	if (debugOutput) log.debug "initialize ran"
}


/*
	logsOff

	Purpose: automatically disable debug logging after 30 mins.

*/
def logsOff(){
	log.warn "debug logging disabled..."
	device.updateSetting("debugOutput",[value:"false",type:"bool"])
}


/*
	parse
    
	In a virtual world this should never be called.
*/
def parse(String description)
{
	if (debugOutput) log.debug "Description is $description"
}

/*
	on
    
	Turns the device on.
*/
def on()
{
	// update on/off status
	if (debugOutput) log.debug "$device ON"
	sendEvent(name: "switch",  value:"on")	
}


/*
	off
    
	Turns the device off.
*/
def off()
{
	// update on/off status
	if (debugOutput) log.debug "$device OFF"
	sendEvent(name: "switch",  value:"off")
}

def setLevel(level) {
	if (debugOutput) log.debug "$device setLevel $level"
	sendEvent(name: "setLevel", value:level)
}


def configure() {}
def refresh() {}
