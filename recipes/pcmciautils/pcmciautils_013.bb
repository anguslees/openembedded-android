require pcmciautils.inc

PR = "r1"

SRC_URI += "file://makefile_fix.patch \
            file://version_workaround.patch \
	    file://modalias_update.patch"

SRC_URI[md5sum] = "5882b7c84f095a7492b1ebe7577dce5a"
SRC_URI[sha256sum] = "7c1adf1a5ebeba124cef4102cbbf9ca65b8493ad6b8fcfb48091e27e983ffc41"
