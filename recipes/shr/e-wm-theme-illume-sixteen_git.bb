DESCRIPTION = "illume SHR theme"
SECTION = "e/utils"
DEPENDS = "edje-native"
LICENSE = "MIT BSD"
SRCREV = "1cc80e26a4558dfc2268b349d9a1f468e515bcfb"
PV = "0.1-${EFL_SRCREV}+gitr${SRCPV}"
PR = "r0"

SRC_URI = "git://git.shr-project.org/repo/shr-themes.git;protocol=http;branch=master"

S = "${WORKDIR}/git/e-wm/${PN}"

do_compile() {
	${STAGING_BINDIR_NATIVE}/edje_cc -id ${S}/images/. -fd ${S}/fonts/. ${S}/illume-sixteen.edc -o ${S}/illume-sixteen.edj
}

do_install() {
        install -d ${D}${datadir}/enlightenment/data/themes/
        install -m 0644 ${S}/illume-sixteen.edj ${D}${datadir}/enlightenment/data/themes/
}

FILES_${PN} = "${datadir}/enlightenment/data/themes/illume-sixteen.edj"


