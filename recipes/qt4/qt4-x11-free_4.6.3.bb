require qt4-x11-free.inc
PR = "${INC_PR}.2"

QT_CONFIG_FLAGS_append_armv6 = " -no-neon "

require qt-${PV}.inc

QT_CONFIG_FLAGS += " \
 -no-embedded \
 -xrandr \
 -x11"
