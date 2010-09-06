DESCRIPTION = "Linux driver for OX16C950 UART Bluetooth cards"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
HOMEPAGE = "http://www.holtmann.org/linux/bluetooth/bt950.html"
DEPENDS = "pcmcia-cs"
RDEPENDS_${PN} = "pcmcia-cs"
PR = "r2"

SRC_URI = "http://www.holtmann.org/linux/bluetooth/bt950-${PV}.tar.gz \
file://makefile.patch"

S = "${WORKDIR}/bt950-${PV}"

PACKAGES += "kernel-module-bt950-cs"
FILES_kernel-module-bt950-cs = "/etc/pcmcia/bt950.conf /lib/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth/bt950_cs.o"

inherit module

do_install() {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth/ \
				${D}${sysconfdir}/pcmcia/
	install -m 0644 bt950_cs.o ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/bluetooth/
	install -m 0644 bt950.conf ${D}${sysconfdir}/pcmcia/
}


SRC_URI[md5sum] = "41d6a2dfe88693b5ec999d9ae4e97aac"
SRC_URI[sha256sum] = "e3ff9a7f6dead90d55f1a4a2b1277e3bf5f0ec44ccb6ba8f375acbbedb1263c1"
