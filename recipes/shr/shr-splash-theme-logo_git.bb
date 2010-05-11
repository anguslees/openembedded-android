DESCRIPTION = "SHR splash screen - SHR logo theme"
SECTION = "x11/data"
LICENSE = "MIT BSD"
SRCREV = "1cc80e26a4558dfc2268b349d9a1f468e515bcfb"
PV = "0.1+gitr${SRCPV}"
PR = "r1"

SRC_URI = "git://git.shr-project.org/repo/shr-themes.git;protocol=http;branch=master"
S = "${WORKDIR}/git/shr-splash/${PN}"

ALTERNATIVE_PRIORITY = 3

require shr-splash-theme.inc

