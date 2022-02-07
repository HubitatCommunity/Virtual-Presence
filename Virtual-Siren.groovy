/*
 * Import URL: https://raw.githubusercontent.com/HubitatCommunity/Virtual-Siren/master/Virtual_Siren.groovy
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
	definition(name: "Virtual Siren Driver", namespace: "csteele", author: "C Steele", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/Virtual-Siren/master/Virtual-Siren.groovy")
	{
 		capability "Switch"
 		capability "Configuration"
 		capability "Actuator"
 		capability "Refresh"
 		capability "Battery"
 		capability "Chime"
 		capability "Tone"
 		capability "Alarm"
 		capability "Switch"
 		capability "SpeechSynthesis"

 		command "testAlarm1"
 		command "testAlarm2"
 		command "testChime1"
 		command "testChime2"
 		command "testChime3"
 		command "testMelody1"
 		command "testMelody2"
 		command "testMelody3"
 		command "testTone1"
 		command "testTone2"
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
void on()
{
	// The server will update on/off status
	eventProcess(name: "switch", value: "on", unit: '')
	
}


/*
	off
    
	Turns the device off.
*/
void off()
{
	// The server will update on/off status
	eventProcess(name: "switch", value: "off", unit: '')
}

// switch, battery, soundName, soundEffects, status, alarm

void beep() { log.trace "${device.displayName}: beep ran" }
void both() { log.trace "${device.displayName}: both ran" }
void configure() { log.trace "${device.displayName}: configure ran" }
void playSound(val) { log.trace "${device.displayName}: playSound $val ran" }
void refresh() { log.trace "${device.displayName}: refresh ran" }
void siren() { log.trace "${device.displayName}: siren ran" }
void speak(val, vol, nam) {
	eventProcess(name: "soundName", value: nam, unit: '')
 log.trace "${device.displayName}: speak $val, $vol, $nam ran"
}
void stop() { log.trace "${device.displayName}: stop ran" }
void strobe() { log.trace "${device.displayName}: strobe ran" }
void testAlarm1() { log.trace "${device.displayName}: testAlarm1 ran" }
void testAlarm2() { log.trace "${device.displayName}: testAlarm2 ran" }
void testChime1() { log.trace "${device.displayName}: testChime1 ran" }
void testChime2() { log.trace "${device.displayName}: testChime2 ran" }
void testChime3() { log.trace "${device.displayName}: testChime3 ran" }
void testMelody1() { log.trace "${device.displayName}: testMelody1 ran" }
void testMelody2() { log.trace "${device.displayName}: testMelody2 ran" }
void testMelody3() { log.trace "${device.displayName}: testMelody3 ran" }
void testTone1() { log.trace "${device.displayName}: testTone1 ran" }
void testTone2() { log.trace "${device.displayName}: testTone2 ran" }


void eventProcess(Map evt) {
    if (device.currentValue(evt.name).toString() != evt.value.toString()) {
        if (!evt.descriptionText) {
            if (evt.unit != null) {
                evt.descriptionText = "${device.displayName} ${evt.name} is ${evt.value}${evt.unit}"
            } else {
                evt.descriptionText = "${device.displayName} ${evt.name} is ${evt.value}"
            }
        }
        sendEvent(evt)
        if (txtEnable) log.info evt.descriptionText
    }
}
