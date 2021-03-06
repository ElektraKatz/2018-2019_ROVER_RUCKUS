package club.towr5291.Concepts;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import club.towr5291.functions.Constants;
import club.towr5291.functions.FileLogger;
import club.towr5291.libraries.TOWR5291LEDControl;
import club.towr5291.opmodes.OpModeMasterLinear;


/**
 * Created by Ian Haden TOWR5291 on 6/27/2018.
 * This OpMode is to demonstrate how to use the LED Class
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 * Modification history
 * Edited by:
 * Ian Haden 06/27/2018 -> Initial creation
 * Ian Haden 07/27/2018 -> Added comments and updated config settings
 */

@TeleOp(name = "Concept LEDs", group = "5291Concept")
//@Disabled
public class ConceptLEDs extends OpModeMasterLinear {

    //set up the variables for the logger
    final String TAG = "Concept LEDs Demo";
    private ElapsedTime runtime = new ElapsedTime();
    private FileLogger fileLogger;
    private int debug = 3;
    private int loop = 0;
    private TOWR5291LEDControl LEDs;
    private Constants.LEDState LEDStatus = Constants.LEDState.STATE_NULL;


    @Override
    public void runOpMode() throws InterruptedException {
        fileLogger = new FileLogger(runtime, debug, true);
        fileLogger.open();
        fileLogger.write("Time,SysMS,Thread,Event,Desc");
        fileLogger.writeEvent(1,TAG, "Log Started");
        //LEDs = new TOWR5291LEDControl(hardwareMap, "lg", "lr", "lb", "rg", "rr", "rb");
        LEDs = new TOWR5291LEDControl(hardwareMap);
        //LEDs.setLEDControlDemoMode(true);
        //LEDs.setLEDControlObjectColour(Constants.ObjectColours.UNKNOWN);
        //LEDs.setLEDControlAlliance("Red");
        LEDs.setLEDControlDemoMode(false);
        LEDs.setLEDColour(Constants.LEDColours.LED_MAGENTA);
        LEDStatus = Constants.LEDState.STATE_FLASHC_COLOUR;
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //the main loop.  this is where the action happens
        while (opModeIsActive()) {
            LEDStatus = LEDs.LEDControlUpdate(LEDStatus);
            //this will log only when debug is at level 3 or above
            fileLogger.writeEvent(3, TAG, "Runtime " + runtime + " - LEDStatus " + LEDStatus);
            telemetry.addLine("runtime " + runtime + " - LEDStatus " + LEDStatus);
            telemetry.update();
        }
        //stop the log
        if (fileLogger != null) {
            fileLogger.writeEvent(1, TAG, "Stopped");
            fileLogger.close();
            fileLogger = null;
        }
    }
}

