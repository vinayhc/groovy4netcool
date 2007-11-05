/* Copyright (C) 2007 iFountain LLC. All rights reserved.
*  Test to update event fields.
*/ 

myEvent = new ncEvent()

myEvent.getEvent(serial)
assert myEvent.Serial == serial

myEvent.TaskList = 1
myEvent.EventText = "Updated for testing"

myEvent.update()
assert myEvent.TaskList == 1
assert myEvent.EventText == "Updated for testing"