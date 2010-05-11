DESCRIPTION = "GNU unit testing framework, written in Expect and Tcl"
LICENSE = "GPL"
SECTION = "devel"
PR = "r1"

SRC_URI = "ftp://ftp.gnu.org/gnu/dejagnu/dejagnu-${PV}.tar.gz"

inherit autotools


SRC_URI[md5sum] = "053f18fd5d00873de365413cab17a666"
SRC_URI[sha256sum] = "d0fbedef20fb0843318d60551023631176b27ceb1e11de7468a971770d0e048d"
