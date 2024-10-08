// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edgetrip_v1.proto

// Protobuf Java Version: 3.25.0
package tme.edgetrip.protos;

/**
 * <pre>
 * Driving score message
 * </pre>
 *
 * Protobuf type {@code edgetrip_v1.DrivingScore}
 */
public final class DrivingScore extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:edgetrip_v1.DrivingScore)
    DrivingScoreOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DrivingScore.newBuilder() to construct.
  private DrivingScore(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DrivingScore() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new DrivingScore();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return tme.edgetrip.protos.EdgeTripProtos.internal_static_edgetrip_v1_DrivingScore_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return tme.edgetrip.protos.EdgeTripProtos.internal_static_edgetrip_v1_DrivingScore_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            tme.edgetrip.protos.DrivingScore.class, tme.edgetrip.protos.DrivingScore.Builder.class);
  }

  public static final int ECOSTARTSCORE_FIELD_NUMBER = 1;
  private double ecoStartScore_ = 0D;
  /**
   * <pre>
   * ECO Start score value
   * </pre>
   *
   * <code>double ecoStartScore = 1;</code>
   * @return The ecoStartScore.
   */
  @java.lang.Override
  public double getEcoStartScore() {
    return ecoStartScore_;
  }

  public static final int ECOSTEADYSCORE_FIELD_NUMBER = 2;
  private double ecoSteadyScore_ = 0D;
  /**
   * <pre>
   * ECO Steady score value
   * </pre>
   *
   * <code>double ecoSteadyScore = 2;</code>
   * @return The ecoSteadyScore.
   */
  @java.lang.Override
  public double getEcoSteadyScore() {
    return ecoSteadyScore_;
  }

  public static final int ECODECELSCORE_FIELD_NUMBER = 3;
  private double ecoDecelScore_ = 0D;
  /**
   * <pre>
   * ECO Decel score value
   * </pre>
   *
   * <code>double ecoDecelScore = 3;</code>
   * @return The ecoDecelScore.
   */
  @java.lang.Override
  public double getEcoDecelScore() {
    return ecoDecelScore_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (java.lang.Double.doubleToRawLongBits(ecoStartScore_) != 0) {
      output.writeDouble(1, ecoStartScore_);
    }
    if (java.lang.Double.doubleToRawLongBits(ecoSteadyScore_) != 0) {
      output.writeDouble(2, ecoSteadyScore_);
    }
    if (java.lang.Double.doubleToRawLongBits(ecoDecelScore_) != 0) {
      output.writeDouble(3, ecoDecelScore_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (java.lang.Double.doubleToRawLongBits(ecoStartScore_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(1, ecoStartScore_);
    }
    if (java.lang.Double.doubleToRawLongBits(ecoSteadyScore_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(2, ecoSteadyScore_);
    }
    if (java.lang.Double.doubleToRawLongBits(ecoDecelScore_) != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(3, ecoDecelScore_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof tme.edgetrip.protos.DrivingScore)) {
      return super.equals(obj);
    }
    tme.edgetrip.protos.DrivingScore other = (tme.edgetrip.protos.DrivingScore) obj;

    if (java.lang.Double.doubleToLongBits(getEcoStartScore())
        != java.lang.Double.doubleToLongBits(
            other.getEcoStartScore())) return false;
    if (java.lang.Double.doubleToLongBits(getEcoSteadyScore())
        != java.lang.Double.doubleToLongBits(
            other.getEcoSteadyScore())) return false;
    if (java.lang.Double.doubleToLongBits(getEcoDecelScore())
        != java.lang.Double.doubleToLongBits(
            other.getEcoDecelScore())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ECOSTARTSCORE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getEcoStartScore()));
    hash = (37 * hash) + ECOSTEADYSCORE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getEcoSteadyScore()));
    hash = (37 * hash) + ECODECELSCORE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        java.lang.Double.doubleToLongBits(getEcoDecelScore()));
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static tme.edgetrip.protos.DrivingScore parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static tme.edgetrip.protos.DrivingScore parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static tme.edgetrip.protos.DrivingScore parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static tme.edgetrip.protos.DrivingScore parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(tme.edgetrip.protos.DrivingScore prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Driving score message
   * </pre>
   *
   * Protobuf type {@code edgetrip_v1.DrivingScore}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:edgetrip_v1.DrivingScore)
      tme.edgetrip.protos.DrivingScoreOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return tme.edgetrip.protos.EdgeTripProtos.internal_static_edgetrip_v1_DrivingScore_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return tme.edgetrip.protos.EdgeTripProtos.internal_static_edgetrip_v1_DrivingScore_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              tme.edgetrip.protos.DrivingScore.class, tme.edgetrip.protos.DrivingScore.Builder.class);
    }

    // Construct using tme.edgetrip.protos.DrivingScore.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      ecoStartScore_ = 0D;
      ecoSteadyScore_ = 0D;
      ecoDecelScore_ = 0D;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return tme.edgetrip.protos.EdgeTripProtos.internal_static_edgetrip_v1_DrivingScore_descriptor;
    }

    @java.lang.Override
    public tme.edgetrip.protos.DrivingScore getDefaultInstanceForType() {
      return tme.edgetrip.protos.DrivingScore.getDefaultInstance();
    }

    @java.lang.Override
    public tme.edgetrip.protos.DrivingScore build() {
      tme.edgetrip.protos.DrivingScore result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public tme.edgetrip.protos.DrivingScore buildPartial() {
      tme.edgetrip.protos.DrivingScore result = new tme.edgetrip.protos.DrivingScore(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(tme.edgetrip.protos.DrivingScore result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.ecoStartScore_ = ecoStartScore_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.ecoSteadyScore_ = ecoSteadyScore_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.ecoDecelScore_ = ecoDecelScore_;
      }
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof tme.edgetrip.protos.DrivingScore) {
        return mergeFrom((tme.edgetrip.protos.DrivingScore)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(tme.edgetrip.protos.DrivingScore other) {
      if (other == tme.edgetrip.protos.DrivingScore.getDefaultInstance()) return this;
      if (other.getEcoStartScore() != 0D) {
        setEcoStartScore(other.getEcoStartScore());
      }
      if (other.getEcoSteadyScore() != 0D) {
        setEcoSteadyScore(other.getEcoSteadyScore());
      }
      if (other.getEcoDecelScore() != 0D) {
        setEcoDecelScore(other.getEcoDecelScore());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 9: {
              ecoStartScore_ = input.readDouble();
              bitField0_ |= 0x00000001;
              break;
            } // case 9
            case 17: {
              ecoSteadyScore_ = input.readDouble();
              bitField0_ |= 0x00000002;
              break;
            } // case 17
            case 25: {
              ecoDecelScore_ = input.readDouble();
              bitField0_ |= 0x00000004;
              break;
            } // case 25
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private double ecoStartScore_ ;
    /**
     * <pre>
     * ECO Start score value
     * </pre>
     *
     * <code>double ecoStartScore = 1;</code>
     * @return The ecoStartScore.
     */
    @java.lang.Override
    public double getEcoStartScore() {
      return ecoStartScore_;
    }
    /**
     * <pre>
     * ECO Start score value
     * </pre>
     *
     * <code>double ecoStartScore = 1;</code>
     * @param value The ecoStartScore to set.
     * @return This builder for chaining.
     */
    public Builder setEcoStartScore(double value) {

      ecoStartScore_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * ECO Start score value
     * </pre>
     *
     * <code>double ecoStartScore = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearEcoStartScore() {
      bitField0_ = (bitField0_ & ~0x00000001);
      ecoStartScore_ = 0D;
      onChanged();
      return this;
    }

    private double ecoSteadyScore_ ;
    /**
     * <pre>
     * ECO Steady score value
     * </pre>
     *
     * <code>double ecoSteadyScore = 2;</code>
     * @return The ecoSteadyScore.
     */
    @java.lang.Override
    public double getEcoSteadyScore() {
      return ecoSteadyScore_;
    }
    /**
     * <pre>
     * ECO Steady score value
     * </pre>
     *
     * <code>double ecoSteadyScore = 2;</code>
     * @param value The ecoSteadyScore to set.
     * @return This builder for chaining.
     */
    public Builder setEcoSteadyScore(double value) {

      ecoSteadyScore_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * ECO Steady score value
     * </pre>
     *
     * <code>double ecoSteadyScore = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearEcoSteadyScore() {
      bitField0_ = (bitField0_ & ~0x00000002);
      ecoSteadyScore_ = 0D;
      onChanged();
      return this;
    }

    private double ecoDecelScore_ ;
    /**
     * <pre>
     * ECO Decel score value
     * </pre>
     *
     * <code>double ecoDecelScore = 3;</code>
     * @return The ecoDecelScore.
     */
    @java.lang.Override
    public double getEcoDecelScore() {
      return ecoDecelScore_;
    }
    /**
     * <pre>
     * ECO Decel score value
     * </pre>
     *
     * <code>double ecoDecelScore = 3;</code>
     * @param value The ecoDecelScore to set.
     * @return This builder for chaining.
     */
    public Builder setEcoDecelScore(double value) {

      ecoDecelScore_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * ECO Decel score value
     * </pre>
     *
     * <code>double ecoDecelScore = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearEcoDecelScore() {
      bitField0_ = (bitField0_ & ~0x00000004);
      ecoDecelScore_ = 0D;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:edgetrip_v1.DrivingScore)
  }

  // @@protoc_insertion_point(class_scope:edgetrip_v1.DrivingScore)
  private static final tme.edgetrip.protos.DrivingScore DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new tme.edgetrip.protos.DrivingScore();
  }

  public static tme.edgetrip.protos.DrivingScore getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DrivingScore>
      PARSER = new com.google.protobuf.AbstractParser<DrivingScore>() {
    @java.lang.Override
    public DrivingScore parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<DrivingScore> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DrivingScore> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public tme.edgetrip.protos.DrivingScore getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

