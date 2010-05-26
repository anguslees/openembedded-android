require orinoco-modules.inc

RDEPENDS_orinoco-modules-cs += "orinoco-modules"
PR = "r5"

SRC_URI += "\
           file://crosscompile.patch \
           file://monitor-${PV}.patch \
           file://spectrum.conf \
           file://spectrum_fw.h \
	   file://spectrum_cs.c \
           file://orinoco_cs.conf"
S = "${WORKDIR}/orinoco-${PV}"

do_compile_prepend() {
	cp -f ${WORKDIR}/spectrum* ${S}/
}

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/net/
        install -d ${D}${sysconfdir}/pcmcia
        install -m 0644 *${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/net/
        install -m 0644 ${WORKDIR}/spectrum.conf ${D}${sysconfdir}/pcmcia/
        install -m 0644 hermes.conf ${D}${sysconfdir}/pcmcia/
        install -d ${D}${sysconfdir}/modutils
        install -m 0644 ${WORKDIR}/orinoco_cs.conf ${D}${sysconfdir}/modutils/
}

FILES_orinoco-modules-cs = "/lib/modules/${KERNEL_VERSION}/net/*_cs${KERNEL_OBJECT_SUFFIX} /${sysconfdir}"

SRC_URI[md5sum] = "4cea186648c335752bc241418fd54038"
SRC_URI[sha256sum] = "07fbed267421ee26ee4b6bc410b68a10a284072d78e1e495c74865db0470306e"
