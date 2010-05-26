require linux.inc

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_mpc8313e-rdb = "1"
DEFAULT_PREFERENCE_mpc8323e-rdb = "1"
DEFAULT_PREFERENCE_avr32 = "1"

PR = "r13"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.23.tar.bz2;name=kernel \
	   file://binutils-buildid-arm.patch \
           file://kallsyms-missing-include.patch \
           file://defconfig \
	   "

# Bug fixes on the 2.6.23.x stable branch
SRC_URI += "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/patch-2.6.23.17.bz2;apply=yes;name=stablepatch"
# Real-time preemption (includes CFS). This is experimental and requires a different defconfig.
#SRC_URI += "file://patch-2.6.23.12-rt14;apply=yes"
# Only the Completely Fair Scheduler (CFS), the official backport from 2.6.24 (adapted for 2.6.23.17)
SRC_URI += "file://sched-cfs-v2.6.23.12-v24.1.patch"
# Add support for squashfs-lzma (a highly compressed read-only filesystem)
SRC_URI += "http://kamikaze.waninkoko.info/patches/2.6.23/klight1/broken-out/squashfs-lzma-2.6.23.patch;name=squashfspatch"

SRC_URI += "file://time.h.patch"

# The Atmel patch doesn't apply against 2.6.23.12  :( 
SRC_URI_avr32 = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.23.tar.bz2;name=kernel \
                 file://defconfig \
                 http://avr32linux.org/twiki/pub/Main/LinuxPatches/linux-2.6.23.atmel.3.patch.bz2;name=atmelpatch \
                "
SRC_URI_append_em-x270 = "\
	file://em-x270.patch "

SRC_URI_append_cm-x270 = "\
	file://0001-cm-x270-base2.patch \
	file://0002-cm-x270-match-type.patch \
	file://0003-cm-x270-ide.patch \
	file://0004-cm-x270-it8152.patch \
	file://0005-cm-x270-pcmcia.patch \
	file://0006-ramdisk_load.patch \
	file://0007-mmcsd_large_cards-r0.patch \
	file://0008-cm-x270-nand-simplify-name.patch"

SRC_URI_append_mpc8313e-rdb = "\
	file://mpc831x-nand.patch \
	file://mpc8313e-rdb-leds.patch \
	file://mpc8313e-rdb-rtc.patch"

SRC_URI_append_mpc8323e-rdb = "\
	file://mpc832x-leds.patch" 

CMDLINE_cm-x270 = "console=${CMX270_CONSOLE_SERIAL_PORT},38400 monitor=8 bpp=16 mem=64M mtdparts=physmap-flash.0:256k(boot)ro,0x180000(kernel),-(root);cm-x270-nand:64m(app),-(data) rdinit=/sbin/init root=mtd3 rootfstype=jffs2"

FILES_kernel-image_cm-x270 = ""

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


addtask compulab_image after do_package before do_build


SRC_URI[kernel.md5sum] = "2cc2fd4d521dc5d7cfce0d8a9d1b3472"
SRC_URI[kernel.sha256sum] = "d4e67c0935ffb2a4158234bff92cc791b83177866009fc9b2214104e0038dbdb"
SRC_URI[stablepatch.md5sum] = "7d2dbdf18868b496548d4375d8c67d3a"
SRC_URI[stablepatch.sha256sum] = "04dfd3f22dc3abce7d7509c0836350540b60cadd19292be70a2a2900c5fbe7b8"
SRC_URI[squashfspatch.md5sum] = "4f9b572c2381c457d866cda9300208de"
SRC_URI[squashfspatch.sha256sum] = "e0f476d04af5921d4a44ab312a03fc6c7122b71adff6e38add8aa188d9969784"
SRC_URI[atmelpatch.md5sum] = "649a8c56f8c5cc2c8deee0055f7ed5b0"
SRC_URI[atmelpatch.sha256sum] = "80de5fe71259921fcd4dd3182f58791ec6eb8380e6030fac10c1d2a53a10133e"
