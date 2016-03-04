# __author__ = 'bumin'
#This project is a simplified version of plane interface implement by python
#There are three planes in the interface, the user can change the speed of all the planes
#Also, the user can use the start/stop buton to control the planes

try:
    import tkinter
except ImportError:
    import Tkinter as tkinter
    import tkMessageBox

############################################################
# MYCANVAS CLASS
############################################################

class MyCanvas(tkinter.Canvas):
    
    # Set initial stuff
    def __init__(self, parent):
        tkinter.Canvas.__init__(self, parent)
        self["background"] = "grey"

        self.pack(fill="both", expand=1)
        self._planes = list()

    # Add planes
    def addPlane(self, Plane):
        self._planes.append(Plane)

    # Draw the position of plane after each tick
    def draw(self):
        print ("\nAnimating:")
        for p in self._planes:
            p.tick()

    # The controller to change the speed of the plane
    def change(self):
        for p in self._planes:
            p.changeSpeed(scrollbar.get())

############################################################
# PLANE CLASS
############################################################

class Plane():

    # Set initial position for planes
    def __init__(self, x, y, dx, dy, speed):
        # Stash args as ivars
        self._x = x
        self._y = y
        self._dx = dx
        self._dy = dy
        self._speed = speed

        # Make our graphical object
        # NB self._shape is just an ID number not an object
        # so we HAVE a shape, not ARE a shape
        self._shape = map.create_rectangle(
            self._x, self._y,
            self._x + 30, self._y + 20,
            fill="RED")
        # Bind callback
        map.tag_bind(self._shape, '<Button-1>', self._onclick)

    # Mouse callback for this particular plane
    def _onclick(self, event):
        # Change its color to black
        map.itemconfigure(self._shape, fill="black")

    # Degine the moving pattern for the plane
    def tick(self):
        self._x += self._dx + self._speed
        self._y += self._dy + self._speed
        print(self._speed)
        print(self._x)
        map.coords(self._shape, self._x, self._y, self._x + 30, self._y + 20)

    # The control button to change the speed of the plane
    def changeSpeed(self, speed):
        self._speed = speed;


############################################################
# SUBCLASS OF PLANE
############################################################

class ReversePlane(Plane):
    def tick(self):
        self._x -= self._dx + self._speed
        self._y -= self._dy + self._speed
        map.coords(self._shape, self._x, self._y, self._x + 30, self._y + 20)


############################################################
# BUTTON CLASS
############################################################

# The stop button
class StopButton(tkinter.Button):
    def __init__(self, label, parent):
        tkinter.Button.__init__(self, parent)
        self["text"] = label
        self["command"] = self._myCallback
        self.pack()

    def _myCallback(self):
        start.set(False)
        print (self["text"], "was pushed")

# The start button
class StartButton(tkinter.Button):
    def __init__(self, label, parent):
        tkinter.Button.__init__(self, parent)
        self["text"] = label
        self["command"] = self._myCallback
        self.pack()

    def _myCallback(self):
        start.set(True)
        print (self["text"], "was pushed")


############################################################
# MAIN PROGRAM
############################################################

# Main window
top = tkinter.Tk()
# Start and stop button control
start = tkinter.BooleanVar()
start.set(True)

# Create new canvas
map = MyCanvas(top)
b1 = StartButton("Start", top)
b1.pack(side="left")
b2 = StopButton("Stop", top)
b2.pack(side="left")
scrollbar = tkinter.Scale(top, from_=1, to=5, orient=tkinter.HORIZONTAL)
scrollbar.pack(side="right")

# Create some planes in the map and remember them in a list
map.addPlane(Plane(30, 20, 1, 2, 1))
map.addPlane(Plane(100, 20, 2, 1, 1))
map.addPlane(ReversePlane(200, 120, 2, 1, 1))

# Control panel area
controlPanel = tkinter.Frame(top, borderwidth=2, background="lightblue")
controlPanel.pack(side="top")

# Define the clock
def tick():
    if start.get():
        map.change()
        map.draw()
    top.after(1000, tick)

top.after(1000, tick)
top.mainloop()
