/**
 *  Light Link
 *
 *  Copyright 2020 Matt Maher
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
definition(
    name: "Light Link",
    namespace: "bladnman",
    author: "Matt Maher",
    description: "Keep 2 lights in sync",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("When this light changes") {
        input "masterSwitch", "capability.switch", required: true, title: "Master light"
    }
    section("Make this one follow") {
        input "slaveSwitch", "capability.switch", required: true, title: "Slave light"
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	// https://docs.smartthings.com/en/latest/capabilities-reference.html#light
	subscribe(masterSwitch, "switch", switchHandler)
}
def switchHandler(evt) {
    if (evt.value == "on") {
        log.debug "[M@] ON"
    	slaveSwitch.on()
    } else if (evt.value == "off") {
        log.debug "[M@] OFF"
    	slaveSwitch.off()
    }
}