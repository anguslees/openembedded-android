SECTION = "x11/wm"
DESCRIPTION = "Waimea is a highly customizable window manager for the X Window \
system conforming to the latest EWMH specification."
DEPENDS = "cairo libpng xrandr libsvg-cairo libxext"
SRCDATE = "20060814"
PV = "0.0+cvs${SRCDATE}"
LICENSE = "GPL"
SRC_URI = "cvs://anoncvs:@cvs.waimea.org/cvs/waimea;module=waimea;method=pserver"
S = "${WORKDIR}/waimea"

inherit autotools

# FIXME: waimea requires a full libx11, for the Xutf8TextProperty stuff
BROKEN = "1"
