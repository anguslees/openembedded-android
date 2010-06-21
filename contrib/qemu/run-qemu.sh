#!/bin/sh
# set -x
# This script helps in launching QEMU to emulate OE based target systems
# It accepts 2 arguments
# First argument is the target architectures
# second argument is empty if you want to do a complete init or
# 'single' if booting into /bin/sh
# It assumes that bridge is setup on the eth0 device on the host systems

# on debian-like systems
# set up bridge aptitude install bridge-utils
# 
# change /etc/network/interfaces to something like below and restart
# network

#auto lo
#iface lo inet loopback
#
#auto br0
#iface br0 inet static
#        address 10.0.1.37
#        netmask 255.255.0.0
#        gateway 10.0.0.1
#        bridge_ports eth0
#        bridge_maxwait 0
#        bridge_fd 9
#        bridge_stp off
#
#iface eth0 inet ipv4ll
#
#auto eth0

# before using this script make sure that the following variables are set
# as per your build system environment

# oetmpdir -	Same as $TMPDIR set in OE local.conf
# oesrcdir -	Absolute path to OE metadata
# kernel -	Kernel image e.g. uImage, zImage, vmlinux etc.
# image -	The root file system created by OE console-image, x11-image etc.
# imagetype -	Image file system type e.g. ext2, ext3 etc.
# libc -	uclibc/glibc/eglibc
# server -	NFS server hosting the root file system.
# gateway
# netmask
# address -	IP Address of the target. You may have diffrent LAN setup
# hostname -	Name of the target
# nfsdir -	Absolute path to directory having the root file system on NFS
# device
# nfsboot -	'yes' if root file system is to be mounted over NFS, if booting from
#		disk image then set it to 'no'
# networking -	If target should enable networking over bridge set to 'yes' else 'no'
#		This will be set to 'yes' automatically if nfsboot is enabled
# staticip -	Set to 'yes' if IP address is assigned statically or 'no' if getting
#		from dhcp server. Note that this option is not entertained if networking
#		is disabled

supported_archs="{arm mips ppc sh4 x86}"
if [ $# -lt 2 ]; then
    echo -en "
    Usage: `basename $0` <arch> <libc>
    where <arch> is one $supported_archs
    libc is uclibc glibc or eglibc
    Example: `basename $0` arm eglibc
"
    exit 0
fi

arch=$1
libc=$2
mem=256				# memory for guest server in Mb
imagetype="ext2"
networking="no"
nfsboot="no"
staticip="yes"

case $arch in
    arm)
	address="10.0.1.101"
        macaddr="00:16:3e:00:00:01"
	machine="versatilepb"
	gdbport="1234"
	consoleopt="console=ttyAMA0 console=ttyS0"
	rootdisk="sda"
	qemuopts="-nographic"
        kernel="zImage"
        image="native-sdk-image"
        ;;
    mips)
	address="10.0.1.102"
        macaddr="00:16:3e:00:00:02"
	machine="malta"
	gdbport="1235"
        consoleopt="console=ttyS0"
	rootdisk="hda"
	qemuopts="-nographic"
        kernel="vmlinux"
        image="minimalist-image"
        ;;
    ppc|powerpc)
    	arch=ppc
	address="10.0.1.103"
        macaddr="00:16:3e:00:00:03"
	machine="g3beige"
	gdbport="1236"
        consoleopt="console=ttyS0"
	rootdisk="hdc"
	qemuopts="-nographic"
        kernel="vmlinux"
        image="minimalist-image"
        ;;
    sh|sh4)
    	arch=sh4
	address="10.0.1.104"
        macaddr="00:16:3e:00:00:04"
	machine="r2d"
	gdbport="1237"
	mem="512"
        consoleopt="console=ttySC1 noiotrap earlyprintk=sh-sci.1"
	rootdisk="sda"
	qemuopts="-monitor null -serial vc -serial stdio"
        kernel="zImage"
        image="minimalist-image"
        ;;
    x86)
	address="10.0.1.105"
        macaddr="00:16:3e:00:00:05"
	gdbport="1237"
	machine="pc"
        consoleopt="console=ttyS0"
	rootdisk="hda"
	qemuopts="-nographic"
        kernel="bzImage"
        image="minimalist-image"
        ;;
    *)
        echo "Specify one architectures out of $supported_archs to emulate."
   	exit 1
    	;;
    esac

nfsserver="10.0.1.37"		# address of NFS server
gateway="10.0.0.1"		# default gateway
netmask="255.255.0.0"		# subnet mask
hostname="qemu$arch"		# hostname for guest server
nfsdir="/opt/oe/$hostname"	# nfs directory where root file system is
device="eth0"			# interface that guest server will use
gdbit="-redir tcp:2222::22 -gdb tcp::$gdbport"   # debug the kernel using gdb set it to -s
				# add -S to stop after launch and wait for
				# gdb to connect

oetmpdir=/scratch/oe
oesrcdir=$HOME/work/oe/openembedded

nfsopts="rsize=8192,wsize=8192,hard,intr,tcp,nolock"	# nfs options

if [ $nfsboot = "yes" ]; then
	# for NFS root 
	rootfs="root=/dev/nfs rw nfsroot=$nfsserver:$nfsdir,$nfsopts"
	# without networking nfsroot wouldnt be possible so enable it explicitly.
	networking="yes"
else
	# Boot from a Disk Image
	rootfs="root=/dev/$rootdisk rw"
fi

if [ $networking != "yes" ]; then
	ipopt=""
else
if [ $staticip = "yes" ]; then
	# ip format
	#ip=<client-ip>:<server-ip>:<gw-ip>:<netmask>:<hostname>:<device>:<autoconf>
	ipopt="ip=$address::$gateway:$netmask:$hostname:$device:off"
else
	# get IP from DHCP server on network
	ipopt="ip=dhcp"
fi
fi
qemuifup="$oesrcdir/contrib/qemu/qemu-ifup"
qemuifdown="$oesrcdir/contrib/qemu/qemu-ifdown"


if [ $networking = "yes" ]; then
	uid=`whoami`
	iface=`sudo tunctl -b -u $uid`
	netopt="-net nic,vlan=0,macaddr=$macaddr -net tap,vlan=0,ifname=$iface,script=$qemuifup,downscript=$qemuifdown"
else
	netopt="-net none"
fi

if [ "x$3" == "xsingle" ]; then
    init="init=/bin/sh"
else
    init=""
fi

qemupath="$oetmpdir/sysroots/`uname -m`-linux/usr/bin"

if [ $arch = "x86" ]; then
    qemu=$qemupath/qemu
else
    qemu=$qemupath/qemu-system-$arch
fi
kernelimage=$oetmpdir/deploy/$libc/images/qemu$arch/$kernel-qemu$arch.bin
hdimage=$oetmpdir/deploy/$libc/images/qemu$arch/$image-qemu$arch.$imagetype
echo "Starting QEMU ..."
set -x
$qemu -M $machine --snapshot $gdbit -m $mem \
	-kernel $kernelimage -drive file=$hdimage \
	-usb -usbdevice wacom-tablet --no-reboot -localtime \
	$qemuopts \
	-append "$consoleopt $rootfs $ipopt $init debug user_debug=-1" \
	$netopt
set +x
if [ $networking = "yes" ]; then
	#destroy the tap interface
	sudo tunctl -b -d $iface
fi
stty sane
