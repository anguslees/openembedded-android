require linux-kexecboot.inc
require ../linux/linux-rp.checksums.inc

PR = "${INC_PR}.0"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_collie = "-1"
DEFAULT_PREFERENCE_poodle = "1"
DEFAULT_PREFERENCE_qemux86 = "-1"
DEFAULT_PREFERENCE_tosa = "1"
DEFAULT_PREFERENCE_c7x0 = "1"
DEFAULT_PREFERENCE_akita = "1"
DEFAULT_PREFERENCE_spitz = "1"

# Handy URLs
# git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux-2.6.git;protocol=git;tag=ef7d1b244fa6c94fb76d5f787b8629df64ea4046
# ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.18.tar.bz2
# ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/testing/linux-2.6.20-rc4.tar.bz2
# ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/testing/patch-2.6.18-rc6.bz2;patch=1
# ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/snapshots/patch-2.6.18-rc2-git1.bz2;patch=1
# ${KERNELORG_MIRROR}/pub/linux/kernel/people/alan/linux-2.6/2.6.10/patch-2.6.10-ac8.gz;patch=1
# ${KERNELORG_MIRROR}/pub/linux/kernel/people/akpm/patches/2.6/2.6.14-rc2/2.6.14-rc2-mm1/2.6.14-rc2-mm1.bz2;patch=1

# Patches submitted upstream are towards top of this list 
# Hacks should clearly named and at the bottom
SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-2.6.24.tar.bz2;name=kernel \
	   file://0001-time-prevent-the-loop-in-timespec_add_ns-from-bei.patch;patch=1 \
           ${RPSRC}/export_atags-r2.patch;patch=1;status=pending;name=rppatch54 \
           ${RPSRC}/lzo_crypto-r2.patch;patch=1;status=pending;name=rppatch36 \
           ${RPSRC}/corgi_rearrange_lcd-r0.patch;patch=1;status=pending;name=rppatch55 \
	   file://hrw-hostapcard.patch;patch=1;status=pending \
           ${RPSRC}/allow_disable_deferrred-r0.patch;patch=1;name=rppatch56 \
           ${RPSRC}/lzo_jffs2_sysfs-r1.patch;patch=1;name=rppatch1 \
           ${RPSRC}/hx2750_base-r33.patch;patch=1;name=rppatch57 \
           ${RPSRC}/hx2750_bl-r9.patch;patch=1;name=rppatch3 \
           ${RPSRC}/hx2750_pcmcia-r3.patch;patch=1;name=rppatch4 \
           ${RPSRC}/pxa_keys-r8.patch;patch=1;name=rppatch58 \
           ${RPSRC}/tsc2101-r18.patch;patch=1;name=rppatch59 \
           ${RPSRC}/hx2750_test1-r8.patch;patch=1;name=rppatch7 \
           ${RPSRC}/input_power-r10.patch;patch=1;name=rppatch60 \
           ${RPSRC}/pxa25x_cpufreq-r2.patch;patch=1;name=rppatch44 \
           ${RPSRC}/sharpsl_pm_fixes1-r0.patch;patch=1;name=rppatch8 \
           ${RPSRC}/pm_changes-r1.patch;patch=1;name=rppatch9 \
           ${RPSRC}/usb_add_epalloc-r4.patch;patch=1;name=rppatch61 \
           ${RPSRC}/usb_pxa27x_udc-r8.patch;patch=1;name=rppatch62 \
           ${RPSRC}/locomo_kbd_tweak-r1a.patch;patch=1;name=rppatch63 \
           ${RPSRC}/pxa27x_overlay-r8.patch;patch=1;name=rppatch11 \
           ${RPSRC}/w100_extaccel-r2.patch;patch=1;name=rppatch12 \
           ${RPSRC}/w100_extmem-r1.patch;patch=1;name=rppatch13 \
           ${RPSRC}/poodle_pm-r5.patch;patch=1;name=rppatch64 \
           ${RPSRC}/poodle_lcd_hack-r0.patch;patch=1;name=rppatch15 \
           ${RPSRC}/poodle_asoc_fix-r1.patch;patch=1;name=rppatch16 \
           file://pxa27x-resume.patch;patch=1;status=external \
           file://mtd-module.patch;patch=1;status=external \
           file://wm8750-treble.patch;patch=1;status=external \
           file://spitz_h_rewrite.patch;patch=1;status=external \
           file://pxa2xx_udc-clock.patch;patch=1 \
           file://sharpsl-rc-r1.patch;patch=1 \
           file://sharpsl-rc-r2.patch;patch=1 \
           file://squashfs3.3.patch;patch=1;status=external \
#           ${RPSRC}/logo_oh-r1.patch.bz2;patch=1;status=unmergable;name=rppatch17 \
           ${RPSRC}/pxa-linking-bug.patch;patch=1;status=unmergable;name=rppatch18 \
           file://hostap-monitor-mode.patch;patch=1;status=unmergable \
           file://serial-add-support-for-non-standard-xtals-to-16c950-driver.patch;patch=1;status=unmergable \
           ${RPSRC}/mmcsd_large_cards-r1.patch;patch=1;status=hack;name=rppatch19 \
           ${RPSRC}/mmcsd_no_scr_check-r2.patch;patch=1;status=hack;name=rppatch20 \
           ${RPSRC}/integrator_rgb-r1.patch;patch=1;status=hack;name=rppatch21 \
           ${RPSRC}/pxa_cf_initorder_hack-r1.patch;patch=1;status=hack;name=rppatch22 \
           file://pxa-serial-hack.patch;patch=1;status=hack \
           file://connectplus-remove-ide-HACK.patch;patch=1;status=hack \
           file://connectplus-prevent-oops-HACK.patch;patch=1;status=hack \
           file://binutils-buildid-arm.patch;patch=1 \
           file://versatile-armv6.patch;patch=1 \
           file://htcuni.patch;patch=1 \
           file://defconfig"

# FIXMEs before made default	   
# ${RPSRC}/mmcsd_no_scr_check-r1.patch;patch=1;status=hack;name=rppatch50


# Add this to enable pm debug code (useful with a serial lead)
#  ${RPSRC}/sharpsl_pm_debug-r0.patch;patch=1;name=rppatch51

# Disabled until I find the reason this gives issues with cdc_subset
#            ${RPSRC}/usb_rndis_tweaks-r0.patch;patch=1;name=rppatch52 \


SRC_URI_append_collie = "\
	${TKSRC}/mtd-sharp-flash-hack-r4.patch;patch=1;name=tkpatch1 \
	${TKSRC}/mcp-sa11x0-r0.patch;patch=1;name=tkpatch2 \
	${TKSRC}/locomo-r1.patch;patch=1;name=tkpatch3 \
	${TKSRC}/collie-kexec-r1.patch;patch=1;name=tkpatch4 \
	${TKSRC}/sharpsl_pm-4.patch;patch=1;name=tkpatch5 \
	${TKSRC}/collie_pm-3.patch;patch=1;name=tkpatch6 \
	${TKSRC}/ucb1x00_suspend.patch;patch=1;name=tkpatch7 \
	${TKSRC}/collie-ts.patch;patch=1;name=tkpatch8 \
	${TKSRC}/pcmcia_suspend.patch;patch=1;name=tkpatch9 \
	${TKSRC}/locomo_spi-6.patch;patch=1;name=tkpatch10 \
	${TKSRC}/config.patch;patch=1;name=tkpatch11 \
	${TKSRC}/mmc-spi.patch;patch=1;name=tkpatch12 \
	${TKSRC}/linux-2.6.24-SIMpad-rtc-sa1100.patch;patch=1;name=tkpatch13 \
	${TKSRC}/sa1100_spinlock.patch;patch=1;name=tkpatch14 \
	${TKSRC}/sa1100-dma.patch;patch=1;name=tkpatch15 \
"

SRC_URI_append_poodle = "\
           ${RPSRC}/poodle_serial_vcc-r0.patch;patch=1;name=rppatch53 \
           file://poodle_ts.patch;patch=1 \
           file://pxafb.patch;patch=1 \
"

SRC_URI_append_tosa = "\
           file://tosa/0001-Allow-runtime-registration-of-regions-of-memory-that.patch;patch=1 \
           file://tosa/0002-Modify-dma_alloc_coherent-on-ARM-so-that-it-supports.patch;patch=1 \
           file://tosa/0003-Core-MFD-support.patch;patch=1 \
           file://tosa/0004-Add-support-for-tc6393xb-MFD-core.patch;patch=1 \
           file://tosa/0005-Add-support-for-tc6387xb-MFD-core.patch;patch=1 \
           file://tosa/0006-Add-support-for-t7l66xb-MFD-core.patch;patch=1 \
           file://tosa/0007-Common-headers-for-TMIO-MFD-subdevices.patch;patch=1 \
           file://tosa/0008-Nand-driver-for-TMIO-devices.patch;patch=1 \
           file://tosa/0009-FB-driver-for-TMIO-devices.patch;patch=1 \
           file://tosa/0010-OHCI-driver-for-TMIO-devices.patch;patch=1 \
           file://tosa/0011-MMC-driver-for-TMIO-devices.patch;patch=1 \
           file://tosa/0012-Tosa-keyboard-support.patch;patch=1 \
           file://tosa/0013-USB-gadget-pxa2xx_udc-supports-inverted-vbus.patch;patch=1 \
           file://tosa/0014-tosa_udc_use_gpio_vbus.patch.patch;patch=1 \
           file://tosa/0015-sharpsl-export-params.patch;patch=1 \
           file://tosa/0016-This-patch-fixes-the-pxa25x-clocks-definitions-to-ad.patch;patch=1 \
           file://tosa/0026-I-don-t-think-we-should-check-for-IRQs-when-determin.patch;patch=1 \
           file://tosa/0027-Add-LiMn-one-of-the-most-common-for-small-non-recha.patch;patch=1 \
           file://tosa/0028-Add-suspend-resume-wakeup-support-for-pda_power.patch;patch=1 \
           file://tosa/0029-Support-using-VOLTAGE_-properties-for-apm-calculati.patch;patch=1 \
           file://tosa/0030-Core-driver-for-WM97xx-touchscreens.patch;patch=1 \
           file://tosa/0031-Add-chip-driver-for-WM9705-touchscreen.patch;patch=1 \
           file://tosa/0032-Add-chip-driver-for-WM9712-touchscreen.patch;patch=1 \
           file://tosa/0033-Add-chip-driver-for-WM9713-touchscreen.patch;patch=1 \
           file://tosa/0034-Driver-for-WM97xx-touchscreens-in-streaming-mode-on.patch;patch=1 \
           file://tosa/0035-Build-system-and-MAINTAINERS-entry-for-WM97xx-touchs.patch;patch=1 \
           file://tosa/0036-Set-id-to-1-for-wm97xx-subdevices.patch;patch=1 \
           file://tosa/0037-Don-t-lock-the-codec-list-in-snd_soc_dapm_new_widget.patch;patch=1 \
           file://tosa/0038-Don-t-lock-the-codec-list-in-snd_soc_dapm_new_widget.patch;patch=1 \
           file://tosa/0044-fix-tmio_mmc-debug-compilation.patch;patch=1 \
           file://tosa/0045-Update-tmio_ohci.patch;patch=1 \
           file://tosa/0046-patch-tc6393xb-cleanup.patch;patch=1 \
           file://tosa/0047-tc6393xb-use-bitmasks-instead-of-bit-field-structs.patch;patch=1 \
           file://tosa/0048-tc6393xb-GPIO-support.patch;patch=1 \
           file://tosa/0049-platform-support-for-TMIO-on-tosa.patch;patch=1 \
           file://tosa/0050-tosa-update-for-tc6393xb-gpio.patch;patch=1 \
           file://tosa/0051-fix-sound-soc-pxa-tosa.c-to-new-gpio-api.patch;patch=1 \
           file://tosa/0052-tosa-platform-backlight-support.patch;patch=1 \
           file://tosa/0053-sound-soc-codecs-wm9712.c-28.patch;patch=1 \
           file://tosa/0054-sound-soc-codecs-wm9712.c-2.patch;patch=1 \
           file://tosa/0055-Add-GPIO_POWERON-to-the-list-of-devices-that-we-supp.patch;patch=1 \
           file://tosa/0058-Fix-tosakbd-suspend.patch;patch=1 \
           file://tosa/0059-patch-tosa-wakeup-test.patch;patch=1 \
           file://tosa/0060-Add-support-for-power_supply-on-tosa.patch;patch=1 \
           file://tosa/0061-tosa-bat-unify.patch;patch=1 \
           file://tosa/0062-tosa-bat-fix-charging.patch;patch=1 \
           file://tosa/0063-patch-tosa-bat-jacket-detect.patch;patch=1 \
           file://tosa/0064-Export-modes-via-sysfs.patch;patch=1 \
           file://tosa/0065-wm97xx-core-fixes.patch;patch=1 \
           file://tosa/0066-tmiofb_probe-should-be-__devinit.patch;patch=1 \
           file://tosa/0067-modeswitching.patch;patch=1 \
           file://tosa/0068-Preliminary-tosa-denoiser.patch;patch=1 \
           file://tosa/0019-pxa-remove-periodic-mode-emulation-support.patch;patch=1 \
           file://tosa/0020-Provide-dew-device-clock-backports-from-2.6.24-git.patch;patch=1 \
           file://tosa/0021-Add-an-empty-drivers-gpio-directory-for-gpiolib-infr.patch;patch=1 \
           file://tosa/0022-Provide-new-implementation-infrastructure-that-platf.patch;patch=1 \
           file://tosa/0023-This-adds-gpiolib-support-for-the-PXA-architecture.patch;patch=1 \
           file://tosa/0024-Update-Documentation-gpio.txt-primarily-to-include.patch;patch=1 \
           file://tosa/0025-Signed-off-by-Dmitry-Baryshkov-dbaryshkov-gmail.co.patch;patch=1 \
           file://tosa/0039-Add-generic-framework-for-managing-clocks.patch;patch=1 \
           file://tosa/0040-Clocklib-debugfs-support.patch;patch=1 \
           file://tosa/0041-From-80a359e60c2aec59ccf4fca0a7fd20495f82b1d2-Mon-Se.patch;patch=1 \
           file://tosa/0042-Use-correct-clock-for-IrDA-on-pxa.patch;patch=1 \
           file://tosa/0043-Use-clocklib-for-sa1100-sub-arch.patch;patch=1 \
           file://tosa/0056-Support-resetting-by-asserting-GPIO-pin.patch;patch=1 \
           file://tosa/0057-Clean-up-tosa-resetting.patch;patch=1 \
           file://tosa/0001-pxa2xx-ac97-switch-AC-unit-to-correct-state-before.patch;patch=1 \
	   file://tosa/tosa-bl-fixup.diff;patch=1 \
           file://tosa/tmiofb-fix-unaccel.patch;patch=1 \
           "
#           file://tosa/0017-Convert-pxa2xx-UDC-to-use-debugfs.patch;patch=1 \
#           file://tosa/0018-Fix-the-pxa2xx_udc-to-balance-calls-to-clk_enable-cl.patch;patch=1 \

SRC_URI_append_htcuniversal ="\
	file://htcuni-acx.patch;patch=1;status=external \
	"

SRC_URI_append_zylonite ="\
	file://pxa_fb_overlay.patch;patch=1 \
	file://zylonite-boot.patch;patch=1 \
	"

S = "${WORKDIR}/linux-2.6.24"

SRC_URI[kernel.md5sum] = "3f23ad4b69d0a552042d1ed0f4399857"
SRC_URI[kernel.sha256sum] = "413c64fbbcf81244cb5571be4963644a1e81166a2b0f008a016528363b65c5d3"
