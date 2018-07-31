/*
 * (c) Copyright Ymagis S.A.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.mmfive.exceptions;

/**
 * @author MM5_DevTeam
 */
public class ScreenNotFoundExeption extends Exception {

    public ScreenNotFoundExeption() {
        super("The screen was not found");
    }

}
