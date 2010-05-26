SECTION = "kernel"
DESCRIPTION = "Linux kernel for the Compulab PXA270 system"
LICENSE = "GPLv2"
PR = "r6"
do_unpack[depends] += "unzip-native:do_populate_sysroot"

# Note, the compulab package contains a binary NAND driver that is not
# EABI compatible
# if you get a md5 sum error on x270-linux-drv.zip, compulab has probably
# changed the binary.  Remove it and the md5 file from your tmp directory, 
# and rebuild the kernel.  If you still get md5 failures, contact cbrake
# on the #oe IRC channel -- this recipe probably needs updated.

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.16.tar.bz2;name=kernel \
           file://linux-2.6.16.patch \
           file://defconfig \
	   http://www.compulab.co.il/x270/download/x270-linux-drv.zip;name=x270patch"

S = "${WORKDIR}/linux-2.6.16"

COMPATIBLE_HOST = 'arm.*-linux'

inherit kernel
inherit package

ARCH = "arm"

FILES_kernel-image = ""

do_configure_prepend() {
	install -m 0644 ${WORKDIR}/defconfig ${S}/.config
	install -m 0644 ${WORKDIR}/2.6.16/CL_FlashDrv ${S}/drivers/block/cl_flash
}

python do_compulab_image() {
	import os
	import os.path
	import struct

	deploy_dir = bb.data.getVar('DEPLOY_DIR_IMAGE', d, 1)
	kernel_name = os.path.join(deploy_dir, bb.data.expand('${KERNEL_IMAGETYPE}-${MACHINE}.bin', d))

	img_file = os.path.join(deploy_dir, 'zImage-compulab-pxa270.img')

	fo = open(img_file, 'wb')

	image_data = open(kernel_name, 'rb').read()

	# first write size into first 4 bytes
	size_s = struct.pack('i', len(image_data))

	# truncate size if we are running on a 64-bit host
	size_s = size_s[:4]

	fo.write(size_s)
	fo.write(image_data)
	fo.close()
}

addtask compulab_image before do_build after do_deploy

COMPATIBLE_MACHINE = "cm-x270"


SRC_URI[kernel.md5sum] = "9a91b2719949ff0856b40bc467fd47be"
SRC_URI[kernel.sha256sum] = "1200dcc7e60fcdaf68618dba991917a47e41e67099e8b22143976ec972e2cad7"
SRC_URI[x270patch.md5sum] = "951fc1494f03eb9d40e1ae66f462cbe6"
SRC_URI[x270patch.sha256sum] = "51b4526a5670a071a6bde749620e188bc417c9632548164522ca55be07d7813b"
