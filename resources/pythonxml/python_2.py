class LightSwitch:
    def __init__(self):
        self.state = False  # False means the light is off
                            # True means it's on.

    def toggle_light(self, turn_on: bool):
        if turn_on:
            self.state = True
            print("The light is now ON.")
        else:
            self.state = False
            print("The light is now OFF.")