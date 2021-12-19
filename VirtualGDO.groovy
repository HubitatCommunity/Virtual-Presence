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
	public static String version()      {  return "v0.1.0"  }
 
 
import groovy.transform.Field

metadata 
{
	definition(name: "Hybrid GDO", namespace: "csteele", author: "C Steele", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/??/master/??.groovy")
	{
 		capability "Switch"
 		capability "GarageDoorControl"
 		capability "ContactSensor"


	}

      preferences 
      {
//         configParams.each { input it.value.input }
     

          //standard logging options
          input name: "debugOutput", type: "bool", title: "Enable debug logging", defaultValue: true
      }
}

// @Field static Map configParams = [
//         90: [input: [name: "configParam90", type: "enum", title: "Report Type", description: "", defaultValue: 0, options:[0:"Time Interval Only",1:"Time and Change in Watts"]], parameterSize: 1]
// ]

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
	log.trace "Msg: initialize ran"
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
	on
    
	Turns the device on.
*/
void open() { on() }
void on()
{
	// The server will update on/off status
	String descriptionText = "${device.getDisplayName()} is Open"
	sendEvent(name: "switch", value: "on", linkText: deviceName, descriptionText: descriptionText)
	sendEvent(name: "contact", value: "open", linkText: deviceName, descriptionText: descriptionText)
	sendEvent(name: "door", value: "open", linkText: deviceName, descriptionText: descriptionText)
}


/*
	off
    
	Turns the device off.
*/
void close() { off() }
void off()
{
	// The server will update on/off status
	String descriptionText = "${device.getDisplayName()} is Closed"
	sendEvent(name: "switch", value: "off", linkText: deviceName, descriptionText: descriptionText)
	sendEvent(name: "contact", value: "closed", linkText: deviceName, descriptionText: descriptionText)
	sendEvent(name: "door", value: "closed", linkText: deviceName, descriptionText: descriptionText)
}


// Check Version   ***** with great thanks and acknowlegment to Cobra (CobraVmax) for his original code ****
//--> Add per VersionTemplate_Apps.groovy

private setConfigured(configure) {
    updateDataValue("configured", configure)
}

private isConfigured() {
    getDataValue("configured") == "true"
}
