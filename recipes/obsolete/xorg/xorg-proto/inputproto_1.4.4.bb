require xorg-proto-common.inc
PE = "1"
PR = "${INC_PR}.0"

SRC_URI[archive.md5sum] = "3a532379f226c34b36a5c585999f75e4"
SRC_URI[archive.sha256sum] = "63278ef492d6babb9e3efaef84a67a982e5362c51623714c9428f0384007dfe5"

BBCLASSEXTEND = "native nativesdk sdk"
