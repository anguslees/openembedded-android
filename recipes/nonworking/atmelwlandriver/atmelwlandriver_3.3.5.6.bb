SRC_URI = "${SOURCEFORGE_MIRROR}/atmelwlandriver/atmelwlandriver-${PV}.tar.bz2 \
	file://dotconfig \
	file://pcmf502rd.conf \
	file://makefile.patch"
S = "${WORKDIR}/atmelwlandriver"
LICENSE = "GPL"

inherit module
SECTION = "base"

do_configure() {
	install ${WORKDIR}/dotconfig .config
}

CFLAGS = "-DLINUX_OS -D__KERNEL__ -DMODULE -I${S}/src/includes -I${S}/src/includes/pcmcia -I${STAGING_KERNEL_DIR}/include"

do_compile() {
	export INC="${S}/src/includes"
	export OBJDIR="${D}${base_libdir}/modules/${KERNEL_VERSION}/drivers/net/wireless/atmel/"
	export KERNEL_PATH="${STAGING_KERNEL_DIR}"
	export KERNEL_SRC="${STAGING_KERNEL_DIR}"
	export CC="${KERNEL_CC}"
	export LD="${KERNEL_LD}"
	export KERNEL_VERSION="${KERNEL_VERSION}"
	export TOPDIR="${S}"
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	install -d ${OBJDIR}
	#oe_runmake -C src/Pcmcia_Pci final CFLAGS:='${CFLAGS} -DATMEL_WLAN -DRFMD -DINT_ROAM' MODULE:='pcmf502r' LIB:=-RFMD-
	oe_runmake -C src/Pcmcia_Pci final CFLAGS:='${CFLAGS} -DATMEL_WLAN -DRFMD -DINT_ROAM -DREV_D' MODULE:='pcmf502rd' LIB:=-REVD-
	#oe_runmake -C src/Pcmcia_Pci final CFLAGS:='${CFLAGS} -DATMEL_WLAN -DRFMD -DINT_ROAM -DREV_E' MODULE:='pcmf502re' LIB:=-REVE-
}

do_install() {
	install -d ${D}${sysconfdir}/pcmcia
	install -m 0644 ${WORKDIR}/pcmf502rd.conf ${D}${sysconfdir}/pcmcia/

	# remove any maps that were installed
	rm -f ${D}${base_libdir}/modules/${KERNEL_VERSION}/drivers/net/wireless/atmel/*.map
}

SRC_URI[md5sum] = "dd9a11d175ba0fbb62cf7fec5426f5de"
SRC_URI[sha256sum] = "d80b88753725e2ce83add9f80d120c022112527adbf4425368387da1936345f2"
