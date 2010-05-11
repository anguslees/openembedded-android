require linux.inc
require linux-ixp4xx.inc

KERNEL_RELEASE = "2.6.18"

PV = "${KERNEL_RELEASE}+svnr${SRCPV}"
PR = "r0"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${KERNEL_RELEASE}.tar.bz2 \
	   svn://svn.nslu2-linux.org/svnroot/kernel/trunk/patches;module=${KERNEL_RELEASE};proto=http \
	   file://defconfig-${KERNEL_RELEASE}"

S = "${WORKDIR}/linux-${KERNEL_RELEASE}"

do_prepatch() {
        mv ${WORKDIR}/${KERNEL_RELEASE} ${S}/patches && cd ${S} && quilt push -av
        mv patches patches.ixp4xx
        mv .pc .pc.old
        mv ${WORKDIR}/defconfig-${KERNEL_RELEASE} ${WORKDIR}/defconfig
}

addtask prepatch after do_unpack before do_patch

SRC_URI[md5sum] = "296a6d150d260144639c3664d127d174"
SRC_URI[sha256sum] = "c95280ff6c5d2a17788f7cc582d23ae8a9a7ba3f202ec6e4238eaadfce7c163d"
