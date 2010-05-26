# FIXME, consider using kernel staging directory instead of KERNEL_SOURCE which is
# located in the work directory. see module.bbclass

DESCRIPTION = "Philips USB Webcam driver for Linux that supports VGA resolution"
HOMEPAGE = "http://www.saillard.org/linux/pwc"
PRIORITY = "optional"
SECTION = "kernel/modules"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "http://www.saillard.org/linux/pwc/files/pwc-${PV}.tar.bz2 \
           file://Makefile.patch"

S = "${WORKDIR}/pwc-${PV}"

inherit module

CFLAGS = "'-I${KERNEL_SOURCE}/include' "

CFLAGS_append_arm = " '-D__LINUX_ARM_ARCH__=5' "

CFLAGS_append_armeb = " '-D__LINUX_ARM_ARCH__=5' "

EXTRA_OEMAKE = "'V=1' 'CFLAGS=${CFLAGS}' \
                'CC=${KERNEL_CC}' \
                'LD=${KERNEL_LD}' \
                'KDIR=${STAGING_KERNEL_DIR}'"

export TARGET_LDFLAGS = "-L${STAGING_DIR_TARGET}${layout_libdir} \
                         -rpath-link ${STAGING_DIR_TARGET}${layout_libdir}"

do_install() {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/media
        install -m 0644 *${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/usb/media
}

SRC_URI[md5sum] = "8763f3d6fd0f9738ef9854de205a126d"
SRC_URI[sha256sum] = "dad803c34e902e286a3d6fb052f48b826c62a8ad19a54ced2936da7641b62907"
