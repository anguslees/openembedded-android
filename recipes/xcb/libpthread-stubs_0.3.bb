DESCRIPTION = "This library provides weak aliases for pthread functions \
not provided in libc or otherwise available by default."
SECTION = "x11/libs"
LICENSE = "MIT-X"
HOMEPAGE = "http://xcb.freedesktop.org"
PR = "r1"

# For unknown reason doesn't create libpthread-stubs.la
DEFAULT_PREFERENCE = "-1"

PARALLEL_MAKE = ""
#DEPENDS = "xcb-proto xproto libxau libxslt-native"
# DEPENDS += "xsltproc-native gperf-native"

SRC_URI = "http://xcb.freedesktop.org/dist/libpthread-stubs-${PV}.tar.bz2"

inherit autotools pkgconfig

SRC_URI[md5sum] = "e8fa31b42e13f87e8f5a7a2b731db7ee"
SRC_URI[sha256sum] = "35b6d54e3cc6f3ba28061da81af64b9a92b7b757319098172488a660e3d87299"
