require xorg-app-common.inc
PE = "1"

DESCRIPTION = "X display manager"

DEPENDS += " libxmu libxinerama libxpm libxdmcp libxau virtual/libx11 libxext libxdmcp libxt libxaw"

EXTRA_OECONF += " --with-random-device=/dev/urandom"

FILES_${PN}-dbg += "${libdir}/X11/xdm/.debug/*"

SRC_URI[archive.md5sum] = "cc1816bc62a3722ad509373b0b7f30fe"
SRC_URI[archive.sha256sum] = "e1169058775a44b0898351d2f645039b4d8946360285831e553587a147b735e9"
