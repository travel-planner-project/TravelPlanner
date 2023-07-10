import { useState } from 'react'
import styles from './Tapbar.module.scss'
import Icon from '../../Common/Icon'

type TapbarViewProps = {
  currentTap: string
  changeTap: (name: string) => void
}

function TapbarView({ currentTap, changeTap }: TapbarViewProps) {
  console.log(currentTap)
  return (
    <div className={styles.container}>
      <button
        type='button'
        onClick={() => {
          changeTap('timetable')
        }}
        className={styles.tapContent}
      >
        {currentTap === 'timetable' ? (
          <Icon name='alarm-blue' size={20} />
        ) : (
          <Icon name='alarm-gray' size={20} />
        )}
      </button>
      <button
        type='button'
        onClick={() => {
          changeTap('checkbox')
        }}
        className={styles.tapContent}
      >
        {currentTap === 'checkbox' ? (
          <Icon name='check-square-blue' size={20} />
        ) : (
          <Icon name='check-square-gray' size={20} />
        )}
      </button>
      <button
        type='button'
        onClick={() => {
          changeTap('text')
        }}
        className={styles.tapContent}
      >
        {currentTap === 'text' ? (
          <Icon name='file-text-fill-blue' size={20} />
        ) : (
          <Icon name='file-text-fill-gray' size={20} />
        )}
      </button>
      <button
        type='button'
        onClick={() => {
          changeTap('payment')
        }}
        className={styles.tapContent}
      >
        {currentTap === 'payment' ? (
          <Icon name='calculator-blue' size={20} />
        ) : (
          <Icon name='calculator-gray' size={20} />
        )}
      </button>
    </div>
  )
}

type TapbarProps = {
  currentTap: string
  changeTap: React.Dispatch<React.SetStateAction<string>>
}

function Tapbar({ currentTap, changeTap }: TapbarProps) {
  const handleClickTap = (name: string) => {
    changeTap(name)
  }
  return <TapbarView currentTap={currentTap} changeTap={handleClickTap} />
}

export default Tapbar
