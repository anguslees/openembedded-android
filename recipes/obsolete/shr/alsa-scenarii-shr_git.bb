DESCRIPTION = "Package for the different scenarios used by Openmoko"
SECTION = "openmoko/base"
SRCREV = "8e89c8dbae569a08cac246a3805fca20988c0ad1"
PV = "1.0+gitr${SRCPV}"
PR = "r4"

COMPATIBLE_MACHINE = "(om-gta01|om-gta02|htcdream)"

SRC_URI = "git://git.shr-project.org/repo/shr-themes.git;protocol=http;branch=master"

S = "${WORKDIR}/git/${PN}/${MACHINE_ARCH}"

do_install() {
    install -d ${D}${datadir}/shr/scenarii/
    install -m 644 ${S}/*.state ${D}${datadir}/shr/scenarii/
    install -d ${D}${sysconfdir}/
    install -m 644 ${S}/stereoout.state ${D}${sysconfdir}/asound.state
}

PACKAGE_ARCH_${PN} = "${MACHINE_ARCH}"

FILES_${PN} += "${datadir}/shr"

CONFFILES_${PN} += "${datadir}/shr/scenarii/*"
RPROVIDES_${PN} = "openmoko-alsa-scenarios virtual/alsa-scenarios"

