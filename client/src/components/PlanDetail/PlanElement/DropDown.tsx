import { useState } from 'react'
import styles from './DropDown.module.scss'
import Icon from '../../Common/Icon'

type DropDownProps = {
  options: string[]
}

function DropDown({ options }: DropDownProps) {
  const initialTitleValue = '일정 분류'
  const [isOptionOpened, setIsOptionOpened] = useState<boolean>(false)
  const [selectedOptionTitle, setSelectedOptionTitle] = useState<string>(initialTitleValue)

  const handleChangeCurrentOption = (option: string) => {
    setSelectedOptionTitle(option)
    setIsOptionOpened(false)
  }

  const handleOpenOptions = () => {
    setIsOptionOpened(prev => !prev)
  }

  return (
    <ul className={styles.listContainer}>
      <button
        className={isOptionOpened ? styles.open : styles.listTitle}
        type='button'
        onClick={handleOpenOptions}
      >
        <p
          className={
            selectedOptionTitle === initialTitleValue ? styles.initialTitle : styles.selectedTitle
          }
        >
          {selectedOptionTitle}
        </p>

        <Icon name='arrow-down' size={16} />
      </button>
      {isOptionOpened && (
        <div className={styles.listBox}>
          {options.map(option => {
            return (
              <li className={styles.list} key={crypto.randomUUID()}>
                <button type='button' onClick={() => handleChangeCurrentOption(option)}>
                  {option}
                </button>
              </li>
            )
          })}
        </div>
      )}
    </ul>
  )
}

export default DropDown
