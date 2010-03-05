require linux.inc

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"

PR = "r3"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.23.tar.bz2 \
	   ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/testing/v2.6.24/patch-2.6.24-rc5.bz2;patch=1 \
           file://defconfig \
	   "

S = "${WORKDIR}/linux-2.6.23"

SRC_URI_append_cm-x270 = "\
	file://0001-cm-x270-match-type.patch;patch=1 \
	file://0002-ramdisk_load.patch;patch=1 \
	file://0003-mmcsd_large_cards-r0.patch;patch=1 \
	file://0004-cm-x270-nand-simplify-name.patch;patch=1 \
	file://0005-cmx270-pci.patch;patch=1"

SRC_URI_append_gesbc-9302 = "file://0001-gesbc-nand.patch;patch=1"

CMDLINE_cm-x270 = "console=${CMX270_CONSOLE_SERIAL_PORT},38400 monitor=8 bpp=16 mem=64M mtdparts=physmap-flash.0:256k(boot)ro,0x180000(kernel),-(root);cm-x270-nand:64m(app),-(data) rdinit=/sbin/init root=mtd3 rootfstype=jffs2"

CMDLINE_gesbc-9302 = "console=ttyAM0 root=/dev/ram"

FILES_kernel-image_cm-x270 = ""
FILES_kernel-image_gesbc-9302 = ""

python do_compulab_image() {
	import os
	import os.path
	import struct

	machine = bb.data.getVar('MACHINE', d, 1)
	if machine == "cm-x270":
	    deploy_dir = bb.data.getVar('DEPLOY_DIR_IMAGE', d, 1)
	    kernel_file = os.path.join(deploy_dir, bb.data.expand('${KERNEL_IMAGE_BASE_NAME}', d) + '.bin')
	    img_file = os.path.join(deploy_dir, bb.data.expand('${KERNEL_IMAGE_BASE_NAME}', d) + '.cmx270')

	    fo = open(img_file, 'wb')

	    image_data = open(kernel_file, 'rb').read()

	    # first write size into first 4 bytes
	    size_s = struct.pack('i', len(image_data))

	    # truncate size if we are running on a 64-bit host
	    size_s = size_s[:4]

	    fo.write(size_s)
	    fo.write(image_data)
	    fo.close()

	    os.chdir(deploy_dir)
	    link_file = bb.data.expand('${KERNEL_IMAGE_SYMLINK_NAME}', d) + '.cmx270'
	    img_file = bb.data.expand('${KERNEL_IMAGE_BASE_NAME}', d) + '.cmx270'
	    try:
		os.unlink(link_file)
	    except:
		pass
	    os.symlink(img_file, link_file)
}

addtask compulab_image after do_deploy before do_build

