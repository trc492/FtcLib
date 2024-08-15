/*
 * Copyright (c) 2023 Titan Robotics Club (http://www.titanrobotics.com)
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
 */

package ftclib.motor;

import androidx.annotation.NonNull;

import java.util.Arrays;

import trclib.motor.TrcServo;

/**
 * This class creates an FRC platform specific servo with the specified parameters.
 */
public class FtcServoActuator
{
    /**
     * This class contains all the parameters for creating the servo.
     */
    public static class Params
    {
        private String primaryServoName = null;
        private boolean primaryServoInverted = false;

        private String followerServoName = null;
        private boolean followerServoInverted = false;

        private double logicalPosMin = 0.0;
        private double logicalPosMax = 1.0;

        private double physicalPosMin = 0.0;
        private double physicalPosMax = 1.0;

        private Double maxStepRate = null;

        private double presetTolerance = 0.0;
        private double[] positionPresets = null;

        /**
         * This method returns the string format of the servoParams info.
         *
         * @return string format of the servo param info.
         */
        @NonNull
        @Override
        public String toString()
        {
            return "primaryServoName=" + primaryServoName +
                   ",primaryServoInverted=" + primaryServoInverted +
                   "\nfollowerServoName=" + followerServoName +
                   ",followerServoInverted=" + followerServoInverted +
                   "\nlogicalMin=" + logicalPosMin +
                   ",logicalMax=" + logicalPosMax +
                   "\nphysicalMin=" + physicalPosMin +
                   ",physicalMax=" + physicalPosMax +
                   "\nmaxStepRate=" + maxStepRate +
                   "\nposPresets=" + Arrays.toString(positionPresets);
        }   //toString

        /**
         * This methods sets the parameters of the primary servo.
         *
         * @param name specifies the name of the servo.
         * @param inverted specifies true if the servo is inverted, false otherwise.
         * @return this object for chaining.
         */
        public Params setPrimaryServo(String name, boolean inverted)
        {
            if (name == null)
            {
                throw new IllegalArgumentException("Must provide a valid primary servo name.");
            }

            this.primaryServoName = name;
            this.primaryServoInverted = inverted;
            return this;
        }   //setPrimaryServo

        /**
         * This methods sets the parameter of the follower servo.
         *
         * @param name specifies the name of the servo.
         * @param inverted specifies true if the servo is inverted, false otherwise.
         * @return this object for chaining.
         */
        public Params setFollowerServo(String name, boolean inverted)
        {
            this.followerServoName = name;
            this.followerServoInverted = inverted;
            return this;
        }   //setFollowerServo

        /**
         * This method sets the logical position range of the servo in the range of 0.0 to 1.0.
         *
         * @param minPos specifies the min logical position.
         * @param maxPos specifies the max logical position.
         * @return this object for chaining.
         */
        public Params setLogicalPosRange(double minPos, double maxPos)
        {
            logicalPosMin = minPos;
            logicalPosMax = maxPos;
            return this;
        }   //setLogicalPosRange

        /**
         * This method sets the physical position range of the servo in real world physical unit.
         *
         * @param minPos specifies the min physical position.
         * @param maxPos specifies the max physical position.
         * @return this object for chaining.
         */
        public Params setPhysicalPosRange(double minPos, double maxPos)
        {
            physicalPosMin = minPos;
            physicalPosMax = maxPos;
            return this;
        }   //setPhysicalPosRange

        /**
         * This method sets the maximum stepping rate of the servo. This enables setPower to speed control the servo.
         *
         * @param maxStepRate specifies the maximum stepping rate (physicalPos/sec).
         * @return this parameter object.
         */
        public Params setMaxStepRate(double maxStepRate)
        {
            this.maxStepRate = maxStepRate;
            return this;
        }   //setMaxStepRate

        /**
         * This method sets an array of preset positions for the servo actuator.
         *
         * @param tolerance specifies the preset tolerance.
         * @param posPresets specifies an array of preset positions in scaled unit.
         * @return this object for chaining.
         */
        public Params setPositionPresets(double tolerance, double... posPresets)
        {
            presetTolerance = tolerance;
            positionPresets = posPresets;
            return this;
        }   //setPositionPresets

    }   //class Params

    private final TrcServo primaryServo;

    /**
     * Constructor: Create an instance of the object.
     *
     * @param params specifies the parameters to set up the actuator servo.
     */
    public FtcServoActuator(Params params)
    {
        primaryServo = new FtcServo(params.primaryServoName);
        primaryServo.setInverted(params.primaryServoInverted);
        primaryServo.setLogicalPosRange(params.logicalPosMin, params.logicalPosMax);
        primaryServo.setPhysicalPosRange(params.physicalPosMin, params.physicalPosMax);
        primaryServo.setPosPresets(params.presetTolerance, params.positionPresets);

        if (params.maxStepRate != null)
        {
            primaryServo.setMaxStepRate(params.maxStepRate);
        }

        if (params.followerServoName != null)
        {
            FtcServo followerServo = new FtcServo(params.followerServoName);
            followerServo.setInverted(params.followerServoInverted);
            followerServo.follow(primaryServo);
        }
    }   //FtcServoActuator

    /**
     * This method returns the created primary servo.
     *
     * @return primary servo.
     */
    public TrcServo getServo()
    {
        return primaryServo;
    }   //getServo

}   //class FtcServoActuator
