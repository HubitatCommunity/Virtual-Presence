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
	public static String version()      {  return "v0.0.1"  }
 
 
import groovy.transform.Field

metadata 
{
	definition(name: "Virtual-Dimmer", namespace: "csteele", author: "C Steele", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/Virtual-Presence/master/VirtualDimmer.groovy")
	{
 		capability "Switch"
 		capability "Switch Level"
 		capability "Actuator"
	}

      preferences 
      {
//         configParams.each { input it.value.input }
     

          //standard logging options
          input name: "debugOutput", type: "bool", title: "Enable debug logging", defaultValue: true
          input name: "descTextEnable", type: "bool", title: "Enable Descriptive Text logging", defaultValue: true
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
void on()
{
    String descriptionText = "${device.displayName} was is On"
    if (descTextEnable) log.info "${descriptionText}"
    sendEvent(name:"switch",value:"on",descriptionText: descriptionText, type:"digital")
}


/*
	off
    
	Turns the device off.
*/
void off()
{
    String descriptionText = "${device.displayName} was is Off"
    if (descTextEnable) log.info "${descriptionText}"
    sendEvent(name:"switch",value:"off",descriptionText: descriptionText, type:"digital")
}


/*
	setLevel
	
	sets the dimmer and optionally the duration.
*/
void setLevel(val){
    String descriptionText = "${device.displayName} set to $val level"
    if (descTextEnable) log.info "${descriptionText}"
    sendEvent(name:"level",value:val, descriptionText: descriptionText, type:"digital")
    val ? sendEvent(name:"switch",value:"on",descriptionText: descriptionText, type:"digital") : sendEvent(name:"switch",value:"off",descriptionText: descriptionText, type:"digital")
}

void setLevel(val, dur){
    String descriptionText = "${device.displayName} set to $val level in $dur sec."
    if (descTextEnable) log.info "${descriptionText}"
    runIn(dur.toInteger(), setLevelHandler, [data: [val, descriptionText]])
}

void setLevelHandler(val,descriptionText) {
    sendEvent(name:"level",value:val, descriptionText: descriptionText, type:"digital")
    val ? sendEvent(name:"switch",value:"on",descriptionText: descriptionText, type:"digital") : sendEvent(name:"switch",value:"off",descriptionText: descriptionText, type:"digital")
}

def getThisCopyright(){"&copy; 2020 C Steele "}
