PR = "${INC_PR}.1"

inherit sdk

require gcc-${PV}.inc
require gcc-cross-sdk.inc

SRC_URI += 'file://sdk-libstdc++-includes.patch'

