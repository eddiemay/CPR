/**
 * @fileoverview
 * @enhanceable
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!

goog.provide('proto.cpr.TrainningUI');

goog.require('jspb.Message');


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
proto.cpr.TrainningUI = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.cpr.TrainningUI, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  proto.cpr.TrainningUI.displayName = 'proto.cpr.TrainningUI';
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
proto.cpr.TrainningUI.prototype.toObject = function(opt_includeInstance) {
  return proto.cpr.TrainningUI.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Whether to include the JSPB
 *     instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.cpr.TrainningUI} msg The msg instance to transform.
 * @return {!Object}
 */
proto.cpr.TrainningUI.toObject = function(includeInstance, msg) {
  var f, obj = {
    id: jspb.Message.getField(msg, 1),
    name: jspb.Message.getField(msg, 2),
    description: jspb.Message.getField(msg, 3),
    durationMins: jspb.Message.getField(msg, 4)
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg
  }
  return obj;
};
}


/**
 * Creates a deep clone of this proto. No data is shared with the original.
 * @return {!proto.cpr.TrainningUI} The clone.
 */
proto.cpr.TrainningUI.prototype.cloneMessage = function() {
  return /** @type {!proto.cpr.TrainningUI} */ (jspb.Message.cloneMessage(this));
};


/**
 * required int32 id = 1;
 * @return {number}
 */
proto.cpr.TrainningUI.prototype.getId = function() {
  return /** @type {number} */ (jspb.Message.getField(this, 1));
};


/** @param {number|undefined} value  */
proto.cpr.TrainningUI.prototype.setId = function(value) {
  jspb.Message.setField(this, 1, value);
};


proto.cpr.TrainningUI.prototype.clearId = function() {
  jspb.Message.setField(this, 1, undefined);
};


/**
 * required string name = 2;
 * @return {string}
 */
proto.cpr.TrainningUI.prototype.getName = function() {
  return /** @type {string} */ (jspb.Message.getField(this, 2));
};


/** @param {string|undefined} value  */
proto.cpr.TrainningUI.prototype.setName = function(value) {
  jspb.Message.setField(this, 2, value);
};


proto.cpr.TrainningUI.prototype.clearName = function() {
  jspb.Message.setField(this, 2, undefined);
};


/**
 * required string description = 3;
 * @return {string}
 */
proto.cpr.TrainningUI.prototype.getDescription = function() {
  return /** @type {string} */ (jspb.Message.getField(this, 3));
};


/** @param {string|undefined} value  */
proto.cpr.TrainningUI.prototype.setDescription = function(value) {
  jspb.Message.setField(this, 3, value);
};


proto.cpr.TrainningUI.prototype.clearDescription = function() {
  jspb.Message.setField(this, 3, undefined);
};


/**
 * optional int32 duration_mins = 4;
 * @return {number?}
 */
proto.cpr.TrainningUI.prototype.getDurationMins = function() {
  return /** @type {number?} */ (jspb.Message.getField(this, 4));
};


/** @param {number?|undefined} value  */
proto.cpr.TrainningUI.prototype.setDurationMins = function(value) {
  jspb.Message.setField(this, 4, value);
};


proto.cpr.TrainningUI.prototype.clearDurationMins = function() {
  jspb.Message.setField(this, 4, undefined);
};


