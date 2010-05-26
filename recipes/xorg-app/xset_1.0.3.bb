require xorg-app-common.inc

DESCRIPTION = "user preference utility for X"
LICENSE = "MIT"
DEPENDS += "libxext libxxf86misc libxfontcache libxmu libxp libxau"
PE = "1"

SRC_URI += "file://disable-xkb.patch"

CFLAGS += "-D_GNU_SOURCE"
EXTRA_OECONF = "--disable-xkb"

SRC_URI[archive.md5sum] = "657bbb43ce5470c33665d187c1740566"
SRC_URI[archive.sha256sum] = "0e3fd7d9902442df13c954316a194251e9b9acc197ae939d8b2fae66b8864d11"
