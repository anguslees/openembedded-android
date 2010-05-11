DESCRIPTION = "gpe-snes is a gpe frontend for the snes9x SNES emulator"
SECTION = "games"
LICENSE = "GPLv2"
PRIORITY = "optional"
DEPENDS = "gtk+ libgpewidget libxrandr"

inherit autotools

PR = "r0"

SRC_URI = "http://www.telefonica.net/web2/mteirap/gpe-snes-${PV}.tar.gz"

SRC_URI[md5sum] = "24562cd0cf481d6912621ad9c60cb9cb"
SRC_URI[sha256sum] = "1807b46c5859c0933dba2a795374fff0270c0fd792874d1b256ec0899ab08d69"
