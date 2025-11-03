/***************************************************************************
 *
 * @file main.c
 * @brief Toggles the GPIO_8_CSI (SODIMM_222) pin of a Verdin AM62 SoM via
 * direct memory access. The pin is the GPIO0_41 pin of the AM62x TI SoCs
 *
 * @author Leonardo Costa
 * @date 2025-10-24
 *
 **************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include <unistd.h>
#include <fcntl.h>
#include <sys/mman.h>

// SODIMM_222: GPIO_8_CSI (SoC Ball Name: GPMC0_CSn1)
// Alternate Function: GPIO0_42

#define GPIO_BASE_ADDR 0x00600000 // GPIO0
#define BLOCK_SIZE     (4 * 1024)

// GPIO0_41: Bank 2, Register Bit 10 (Page 128)
#define GPIO_DIR01      (0x10)
#define GPIO_DIR23      (0x38)
#define GPIO_SET_DATA23 (0x40)
#define GPIO_OUT_DATA23 (0x3c)
#define GPIO_CLR_DATA23 (0x44)
#define GPIO0_42_OFFSET 10

volatile void* gpio;

int main() {
    int mem_fd = open("/dev/mem", O_RDWR | O_SYNC);
    if (mem_fd < 0) {
        perror("Could not open memory mapping");
        return EXIT_FAILURE;
    }

    void* gpio_map = mmap(
        NULL,
        BLOCK_SIZE,
        PROT_READ | PROT_WRITE,
        MAP_SHARED,
        mem_fd,
        GPIO_BASE_ADDR
    );

    close(mem_fd);

    if (gpio_map == MAP_FAILED) {
        perror("Failed to create GPIO map");
        return EXIT_FAILURE;
    }

    gpio = (volatile uint32_t *) gpio_map;

    /*printf("Pointer: %p\n", gpio);*/
    /*printf("Offset: %x\n", (1 << GPIO0_42_OFFSET));*/
    // Configure as output
    *((uint32_t*) (gpio + GPIO_DIR23)) &= ~(1 << GPIO0_42_OFFSET);
    /**((uint32_t*) (gpio + GPIO_SET_DATA23)) |= (1 << GPIO0_42_OFFSET);*/
    /*printf("Active\n");*/

    while(1) {
        *((uint32_t*) (gpio + GPIO_SET_DATA23)) |= (1 << GPIO0_42_OFFSET);
        printf("Active\n");
        sleep(1);
        *((uint32_t*) (gpio + GPIO_CLR_DATA23)) |= (1 << GPIO0_42_OFFSET);
        printf("Inactive\n");
        sleep(1);
    }

    /*printf("GPIO_DIR01 @%x: %08x\n", GPIO_BASE_ADDR + GPIO_DIR01, *(gpio + GPIO_DIR01));*/
    /*printf("GPIO_DIR23 @%x: %08x\n", GPIO_BASE_ADDR + GPIO_DIR23, *(gpio + GPIO_DIR23));*/

    /*printf("GPIO_BASE @%p (0x%x): %08x\n", gpio, GPIO_BASE_ADDR , *((uint32_t*) gpio));*/
    /*printf("GPIO_OUT_DATA23 @%p (0x%x): %08x\n", gpio + GPIO_OUT_DATA23, GPIO_BASE_ADDR + GPIO_OUT_DATA23, *((uint32_t*) gpio + GPIO_OUT_DATA23));*/
    /*printf("GPIO_DIR01 @%p (0x%x): %08x\n", gpio + GPIO_DIR01, GPIO_BASE_ADDR + GPIO_DIR01, *((uint32_t*) (gpio + GPIO_DIR01)));*/
    /*printf("GPIO_DIR23 @%p (0x%x): %08x\n", gpio + GPIO_DIR23, GPIO_BASE_ADDR + GPIO_DIR23, *((uint32_t*) (gpio + GPIO_DIR23)));*/
    /*printf("GPIO_SET_DATA23 @%p (0x%x): %08x\n", gpio + GPIO_SET_DATA23, GPIO_BASE_ADDR + GPIO_SET_DATA23, *((uint32_t*) (gpio + GPIO_SET_DATA23)));*/

    return 0;
}
