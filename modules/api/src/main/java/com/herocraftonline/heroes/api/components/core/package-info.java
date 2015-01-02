/**
 * <p>Contains core (integral to normal operation of the plugin in itself) components that in turn get their own
 * get methods as opposed to the generic
 * {@link com.herocraftonline.heroes.api.characters.CharacterBase#getComponent(String)} for convenience sake.</p>
 *
 * <p>In addition, these interfaces provide a form of method consistency as these components contain features that are
 * likely to be used by many extensions of the heroes plugin, such as skills.</p>
 *
 * <p>The guarantee is made that, should the specific features represented by the interface be enabled in configuration,
 * implementations of the interfaces in this package are what will be used to represent these features.</p>
 */
package com.herocraftonline.heroes.api.components.core;

