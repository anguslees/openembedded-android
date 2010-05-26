DESCRIPTION = "Philips USB Webcam driver for Linux that supports VGA resolution"
PRIORITY = "optional"
SECTION = "kernel/modules"
LICENSE = "GPL"
PR = "r1"

SRC_URI = "http://www.saillard.org/linux/pwc/files/pwc-${PV}.tar.bz2 \
	   file://endian-fix.patch \
	   file://Makefile"

S = "${WORKDIR}/pwc-${PV}"

inherit module

do_compile_prepend() {
	cp -f ${WORKDIR}/Makefile ${S}/
}

do_compile () {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
        oe_runmake 'KDIR=${STAGING_KERNEL_DIR}' \
                   'CC=${KERNEL_CC}' \
                   'LD=${KERNEL_LD}'
}

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/media
        install -m 0644 *${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/media
}

SRC_URI[md5sum] = "9ea511431e9475fb78d76f2551303d97"
SRC_URI[sha256sum] = "0ef8f59624736592c7906a265c4d2edba8e860dea90ef23f93ed6d1d9d69bdd2"
