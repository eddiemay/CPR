/**
 * @fileoverview
 * @enhanceable
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!

goog.provide('proto.cpr.CreateReservationRequest');

goog.require('jspb.Message');
goog.require('proto.cpr.ReservationUI');


/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.cpr.CreateReservationRequest = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.cpr.CreateReservationRequest, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.cpr.CreateReservationRequest.displayName = 'proto.cpr.CreateReservationRequest';
}


if (jspb.Message.GENERATE_TO_OBJECT) {
/**
 * Creates an object representation of this proto suitable for use in Soy templates.
 * Field names that are reserved in JavaScript and will be renamed to pb_name.
 * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
 * For the list of reserved names please see:
 *     com.google.apps.jspb.JsClassTemplate.JS_RESERVED_WORDS.
 * @param {boolean=} opt_includeInstance Whether to include the JSPB instance
 *     for transitional soy proto support: http://goto/soy-param-migration
 * @return {!Object}
 */
proto.cpr.CreateReservationRequest.prototype.toObject = function(opt_includeInstance) {
  return proto.cpr.CreateReservationRequest.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.cpr.CreateReservationRequest} msg The msg instance to transform.
 * @return {!Object}
 */
proto.cpr.CreateReservationRequest.toObject = function(includeInstance, msg) {
  var f, obj = {
    reservation: (f = msg.getReservation()) && proto.cpr.ReservationUI.toObject(includeInstance, f)
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg
  }
  return obj;
};
}


/**
 * Creates a deep clone of this proto. No data is shared with the original.
 * @return {!proto.cpr.CreateReservationRequest} The clone.
 */
proto.cpr.CreateReservationRequest.prototype.cloneMessage = function() {
  return /** @type {!proto.cpr.CreateReservationRequest} */ (jspb.Message.cloneMessage(this));
};


/**
 * required ReservationUI reservation = 1;
 * @return {!proto.cpr.ReservationUI}
 */
proto.cpr.CreateReservationRequest.prototype.getReservation = function() {
  return /** @type{!proto.cpr.ReservationUI} */ (
    jspb.Message.getWrapperField(this, proto.cpr.ReservationUI, 1, 1));
};


/** @param {proto.cpr.ReservationUI|undefined} value  */
proto.cpr.CreateReservationRequest.prototype.setReservation = function(value) {
  jspb.Message.setWrapperField(this, 1, value);
};


proto.cpr.CreateReservationRequest.prototype.clearReservation = function() {
  this.setReservation(undefined);
};

