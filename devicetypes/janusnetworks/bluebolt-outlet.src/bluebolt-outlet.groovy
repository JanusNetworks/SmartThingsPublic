/**
 *  Bluebolt Outlet
 *
 *  Copyright 2016 Kevin Corbin
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
 */
 
metadata {
	definition (name: "BlueBolt Outlet", namespace: "JanusNetworks", author: "Kevin Corbin") {

		// capabilities and their corresponding commands
        capability "Switch"
        command "on"
        command "off"
        
        capability "Polling"
        command "poll"
        capability "Refresh"
        command "refresh"
        
        
        // list the commands this device type supports
        
      
	}

	simulator {
		// TODO: define status and reply messages here
        status "on": "#OUTPUT,1,1,100"
        status "off": "#OUTPUT,1,1,0"
        
        reply "on": "~OUTPUT,1,1,100.0"
        reply "off": "~OUTPUT,1,1,0.0"
	}

tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "on", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#79b821", nextState:"turningOff"
			state "off", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#ffffff", nextState:"turningOn"
			state "turningOn", label:'${name}', action:"switch.off", icon:"st.switches.switch.on", backgroundColor:"#79b821", nextState:"turningOff"
			state "turningOff", label:'${name}', action:"switch.on", icon:"st.switches.switch.off", backgroundColor:"#ffffff", nextState:"turningOn"
		}
		//controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 3, inactiveLabel: false) {
		//	state "level", action:"switch level.setLevel"
		//}
		standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat") {
			state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
		}
		
        // the function of the button in "things" list
		main(["switch"])
        // device details
		details(["switch", "levelSliderControl", "refresh"])
	}
}


def poll() {
	log.debug "Executing 'poll'"  
    def results = parent.pollChildren()
    log.debug(results)
    parseEventData(results)       
}

def parseEventData(Map results){
    log.debug "parse event data"
    log.debug(results)
    results.each { name, value ->
    	log.debug('name, value follows')
  		log.debug(name)
        log.debug(value)
        //Parse events and optionally create SmartThings events
    }
}

def refresh() {
	log.debug "Executing 'refresh'" 
	poll();
}

def on() {
	log.debug "entering device.on() "
	parent.on(device)
    sendEvent(name: "switch", value: "on")
}
def off() {
	log.debug "entering device.off()"
    parent.off(device)
    sendEvent(name: "switch", value: "off")


}

