/*
 * Import URL: https://raw.githubusercontent.com/HubitatCommunity/Virtual-Presence/master/VirtualValveHybrid.groovy"
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
	public static String version()      {  return "v1.0.0"  }
 
 
import groovy.transform.Field

metadata 
{
	definition(name: "Virtual Valve Hybrid", namespace: "csteele", author: "C Steele", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/Virtual-Presence/master/VirtualValveHybrid.groovy")
	{
 		capability "Switch"
 		capability "Valve"
 		
	}

      preferences 
      {
     

          //standard logging options
          input name: "debugOutput", type: "bool", title: "Enable debug logging", defaultValue: true
      }
}


/*
	updated
    
	Doesn't do much other than call initialize().
*/
void updated()
{
	initialize()
	unschedule()
      if (debugOutput) runIn(1800,logsOff) //disable debug logs after 30 min
	log.trace "Msg: updated ran"
}



/*
	installed
    
	Doesn't do much other than call initialize().
*/
void installed()
{
	initialize()
	log.trace "Msg: installed ran"
}


/*
	uninstalled
    
	When the device is removed to allow for any necessary cleanup.
*/
void uninstalled()
{
	log.trace "Msg: uninstalled ran"
}

/*
	initialize
    
	Doesn't do anything.
*/
void initialize()
{
	state.Copyright = "${thisCopyright} -- ${version()}"
}


/*
	parse
    
	In a virtual world this should never be called.
*/
void parse(String description)
{
	log.trace "Msg: Description is $description"
}

/*

	generic driver stuff

*/

void logsOff(){
    log.warn "debug logging disabled..."
    device.updateSetting("debugOutput",[value:"false",type:"bool"])
}


/*

	driver command response

*/

/*
	Open
    
	Sets the valve to be copied to switch.
*/
void on() {open()}
void open() {
	sendEvent(name: "switch", unit: "%", value: "on", descriptionText: "${device.displayName} Switch is On")
	sendEvent(name: "valve", value: "open", descriptionText: "${device.displayName} Valve is Open")
	log.info "${device.displayName} is On/Open"
}

/*
	Close
    
	Sets the valve to be copied to switch.
*/
void off() {close()}
void close() {
	sendEvent(name: "switch", value: "off", descriptionText: "${device.displayName} Switch is Off")
	sendEvent(name: "valve", value: "close", descriptionText: "${device.displayName} Valve is Close")
	log.info "${device.displayName} is Off/Closed"
}


String getThisCopyright(){"&copy; 2023 C Steele "}
