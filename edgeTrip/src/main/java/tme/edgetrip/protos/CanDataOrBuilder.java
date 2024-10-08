// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edgetrip_v1.proto

// Protobuf Java Version: 3.25.0
package tme.edgetrip.protos;

public interface CanDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:edgetrip_v1.CanData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * UNIX timestamp of the data point
   * </pre>
   *
   * <code>int32 canTimestamp = 1;</code>
   * @return The canTimestamp.
   */
  int getCanTimestamp();

  /**
   * <pre>
   * Vehicle speed in kph
   * </pre>
   *
   * <code>double speed = 2;</code>
   * @return The speed.
   */
  double getSpeed();

  /**
   * <pre>
   * Front-Back acceleration in m/s2
   * </pre>
   *
   * <code>double accFb = 3;</code>
   * @return The accFb.
   */
  double getAccFb();

  /**
   * <pre>
   * Right-Left acceleration in m/s2
   * </pre>
   *
   * <code>double accRl = 4;</code>
   * @return The accRl.
   */
  double getAccRl();

  /**
   * <pre>
   * Battery state of charge 
   * </pre>
   *
   * <code>int32 stateOfCharge = 5;</code>
   * @return The stateOfCharge.
   */
  int getStateOfCharge();

  /**
   * <code>.edgetrip_v1.CanData.Gear gear = 6;</code>
   * @return The enum numeric value on the wire for gear.
   */
  int getGearValue();
  /**
   * <code>.edgetrip_v1.CanData.Gear gear = 6;</code>
   * @return The gear.
   */
  tme.edgetrip.protos.CanData.Gear getGear();
}
